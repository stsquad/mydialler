/*
    This file is part of MyDialer.

    MyDialer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    MyDialer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with MyDialer.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (C) 2011 Alex Bennée <alex@bennee.com>

    This code is derived from:
      - NubDial, Copyright (C) 2010 Soh Yuan Chin
      - SpellDial, Copyright (C) Lawrence Greenfield
      - http://code.google.com/p/android-business-card/source/browse/trunk/android-business-card/BusinessCard/src/com/example/android/businesscard/ContactAccessor.java, Copyright (C) Android Open Source Project
*/

package com.bennee.mydialer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;

abstract class ContactAccessor {
	public abstract Cursor recalculate(String filter, boolean matchNumAnywhere);
	public abstract IContactSplit getContactSplit();
	public abstract Intent getContactsIntent();
	public abstract Intent getFavouritesIntent();
	public abstract Intent addToContacts(String number);	
	public abstract void setContentResolver(ContentResolver cr);
	
	public Intent getCallLogIntent() {
	    Intent  intent = new Intent(Intent.ACTION_VIEW, null);
        intent.setType("vnd.android.cursor.dir/calls");
        return intent;
	}
	
    /**
     * Static singleton instance of {@link ContactAccessor} holding the
     * SDK-specific implementation of the class.
     */
    private static ContactAccessor sInstance;

    public static synchronized ContactAccessor getInstance(ContentResolver cr) {
        if (sInstance == null) {
            String className;

            /*
             * Check the version of the SDK we are running on. Choose an
             * implementation class designed for that version of the SDK.
             *
             * Unfortunately we have to use strings to represent the class
             * names. If we used the conventional ContactAccessorSdk5.class.getName()
             * syntax, we would get a ClassNotFoundException at runtime on pre-Eclair SDKs.
             * Using the above syntax would force Dalvik to load the class and try to
             * resolve references to all other classes it uses. Since the pre-Eclair
             * does not have those classes, the loading of ContactAccessorSdk5 would fail.
             */
            int sdkVersion = Integer.parseInt(Build.VERSION.SDK);       // Cupcake style
            if (sdkVersion < Build.VERSION_CODES.ECLAIR) {
                className = "com.bennee.mydialer.ContactAccessorSdk3_4";
            } else {
                className = "com.bennee.mydialer.ContactAccessorSdk5";
            }

            /*
             * Find the required class by name and instantiate it.
             */
            try {
                Class<? extends ContactAccessor> clazz =
                        Class.forName(className).asSubclass(ContactAccessor.class);
                sInstance = clazz.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            sInstance.setContentResolver(cr);
        }

        return sInstance;
    }
}
