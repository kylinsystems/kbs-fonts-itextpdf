/******************************************************************************
 * Copyright (C) 2020 ken.longnan@gmail.com                                   *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package com.kylinsystems.kbs.fonts.itextpdf;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;

import org.adempiere.plugin.utils.Version2PackActivator;

public class Activator extends Version2PackActivator {

	@Override
	protected void afterPackIn() {
		// list all fonts files (ttf version)
		Enumeration<URL> urls = context.getBundle().findEntries("/META-INF/fonts", "*.ttf", false);
		if (urls == null)
			return;
		
		// install fonts
		while (urls.hasMoreElements()) {
			URL u = urls.nextElement();
			InputStream input = null;
			try {
				logger.log(Level.INFO, "Installing font from : " + u.getFile());
				
				input = u.openStream();

				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				Font newfont = Font.createFont(Font.TRUETYPE_FONT, input);
				ge.registerFont(newfont);

				logger.log(Level.INFO, "Installed font : [" + "name:" + newfont.getName() 
					+ ",fontname:" + newfont.getFontName() + ",family:" + newfont.getFamily() 
					+ ",psname:" + newfont.getPSName()+ "]");
			} catch (Exception ex) {
				logger.log(Level.SEVERE, ex.getMessage());
			} finally {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
