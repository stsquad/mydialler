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

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
