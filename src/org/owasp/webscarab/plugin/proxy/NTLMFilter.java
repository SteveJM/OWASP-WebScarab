/***********************************************************************
 *
 * $CVSHeader$
 *
 * This file is part of WebScarab, an Open Web Application Security
 * Project utility. For details, please see http://www.owasp.org/
 *
 * Copyright (c) 2002 - 2004 Rogan Dawes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Getting Source
 * ==============
 *
 * Source for this application is maintained at Sourceforge.net, a
 * repository for free software projects.
 * 
 * For details, please see http://www.sourceforge.net/projects/owasp
 *
 */

/*
 * RevealHidden.java
 *
 * Created on July 13, 2003, 7:39 PM
 */

package org.owasp.webscarab.plugin.proxy;

import org.owasp.webscarab.httpclient.HTTPClient;
import org.owasp.webscarab.model.Preferences;
import org.owasp.webscarab.model.Request;
import org.owasp.webscarab.model.Response;
import org.owasp.webscarab.plugin.proxy.ProxyPlugin;

import java.io.IOException;

/**
 *
 * @author  rdawes
 */
public class NTLMFilter extends ProxyPlugin {
    
    private boolean _enabled = false;
    
    /** Creates a new instance of RevealHidden */
    public NTLMFilter() {
        parseProperties();
    }
    
    public void parseProperties() {
        String prop = "NTLMFilter.enabled";
        String value = Preferences.getPreference(prop, "true");
        _enabled = ("true".equalsIgnoreCase( value ) || "yes".equalsIgnoreCase( value ));
    }
    
    public String getPluginName() {
        return new String("Filter NTLM auth");
    }
    
    public void setEnabled(boolean bool) {
        _enabled = bool;
        String prop = "NTLMFilter.enabled";
        Preferences.setPreference(prop,Boolean.toString(bool));
    }

    public boolean getEnabled() {
        return _enabled;
    }
    
    public HTTPClient getProxyPlugin(HTTPClient in) {
        return new Plugin(in);
    }    
    
    private class Plugin implements HTTPClient {
    
        private HTTPClient _in;
        
        public Plugin(HTTPClient in) {
            _in = in;
        }
        
        public Response fetchResponse(Request request) throws IOException {
            Response response = _in.fetchResponse(request);
            if (_enabled) {
                boolean changed = false;
                String[][] headers = response.getHeaders();
                for (int i=0; i< headers.length; i++) {
                    if (headers[i][0].equals("WWW-Authenticate") || 
                        headers[i][0].equals("Proxy-Authenticate")) {
                        String value = headers[i][1];
                        String scheme = value.substring(0, value.indexOf(" "));
                        if (scheme.equalsIgnoreCase("Basic")) {
                            int realmstart = value.indexOf("realm=\"")+7;
                            int realmend = value.indexOf("\"",realmstart);
                            int nextscheme = value.indexOf(",", realmend);
                            String realm = value.substring(realmstart, realmend);
                            if (nextscheme > -1) {
                                headers[i][1] = value.substring(0, nextscheme -1);
                                changed = true;
                            }
                        } else {
                            headers[i][1] = null;
                            changed = true;
                        }
                    }
                }
                if (changed) {
                    response.setHeaders(new String[0][0]);
                    for (int i=0; i< headers.length; i++) {
                        if (headers[i][1] != null) {
                            response.addHeader(headers[i][0], headers[i][1]);
                        }
                    }
                    response.addHeader("X-NTLMFilter", "modified");
                }
            }
            return response;
        }
        
    }
    
}