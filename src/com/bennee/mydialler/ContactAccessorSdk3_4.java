/*
    This file is part of MyDialler.

    MyDialler is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    MyDialler is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with MyDialler.  If not, see <http://www.gnu.org/licenses/>.

    Copyright (C) 2011 Alex Bennée <alex@bennee.com>

    This code is derived from:
      - NubDial, Copyright (C) 2010 Wysie Soh
      - SpellDial, Copyright (C) Lawrence Greenfield
*/

package com.bennee.mydialler;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.Intents.Insert;
import android.provider.Contacts.People;

final class ContactAccessorSdk3_4 extends ContactAccessor {
	private ContentResolver myContentResolver;
	final MyContactSplit myContactSplit;
	
	public ContactAccessorSdk3_4() {
		myContactSplit = new MyContactSplit();
	}	
	
	@Override
	public Intent getContactsIntent() {
		Intent i = new Intent();
		i.setAction(Intent.ACTION_VIEW);
		i.setData(android.provider.Contacts.CONTENT_URI);
		return i;
	}
	
	@Override
	public Intent getFavouritesIntent() {
		return null;
	}

	
	class MyContactSplit implements IContactSplit {
		@Override
		public Uri getCallUri(Uri lookupUri) {
			return lookupUri;
		}

		@Override
		public Uri getContactUri(Uri lookupUri) {
			return lookupUri;
		}

		@Override
		public String getDisplayName(Cursor c) {
            return c.getString(5);
		}

		@Override
		public Uri getLookupUri(Cursor c) {
			String id = c.getString(0);
			return Uri.withAppendedPath(People.CONTENT_URI, id);
		}
	}
	
	@Override
	public IContactSplit getContactSplit() {
		return myContactSplit;
	}

	private final static String upName = "UPPER(" + People.NAME + ")";
    private final static String peopleSql = 
            "(" + upName + " GLOB ? OR " + upName + " GLOB ?) AND " +
            "primary_phone IS NOT NULL";
    private static final String[] PEOPLE_PROJECTION = new String[] {
            Contacts.People._ID, Contacts.People.PRIMARY_PHONE_ID,
            Contacts.People.TYPE, Contacts.People.NUMBER,
            Contacts.People.LABEL, Contacts.People.NAME, };
	
	@Override
	public Cursor recalculate(String filter, boolean matchAnywhere) {
        String[] args = new String[] { filter + "*","*[ ]" + filter + "*" };
        return myContentResolver.query(People.CONTENT_URI, PEOPLE_PROJECTION,
        		peopleSql, args, Contacts.People.DEFAULT_SORT_ORDER);
	}

	@Override
	public void setContentResolver(ContentResolver cr) {
		myContentResolver = cr;
	}

	@Override
	public Intent addToContacts(String number) {
		Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
    	intent.putExtra(Insert.PHONE, number);
    	intent.setType(People.CONTENT_ITEM_TYPE);
		return intent;
	}

}
