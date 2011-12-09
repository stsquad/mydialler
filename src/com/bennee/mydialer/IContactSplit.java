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
      - NubDial, Copyright (C) 2010 Wysie Soh
      - SpellDial, Copyright (C) Lawrence Greenfield
*/

package com.bennee.mydialer;

import android.database.Cursor;
import android.net.Uri;

interface IContactSplit {
	public Uri getLookupUri(Cursor c);
	public String getDisplayName(Cursor c);

	/**
	 * Determine a Uri for sending to Intent.ACTION_CALL or NULL if number can't be determined.
	 * @param lookupUri
	 * @return
	 */
	public Uri getCallUri(Uri lookupUri);

	/**
	 * Determine a Uri for sending to Intent.ACTION_VIEW. Never returns NULL. 
	 * @param lookupUri
	 * @return
	 */
	public Uri getContactUri(Uri lookupUri);
}
