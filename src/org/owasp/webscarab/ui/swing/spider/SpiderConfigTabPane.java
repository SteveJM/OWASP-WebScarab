/*
 * Copyright (c) 2002 owasp.org.
 * This file is part of WebScarab.
 * WebScarab is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * WebScarab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * The valid license text for this file can be retrieved with
 * the call:   java -cp owasp.jar org.owasp.webscarab.LICENSE
 * 
 * If you are not able to view the LICENSE that way, which should
 * always be possible within a valid and working WebScarab release,
 * please write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * SpiderConfigTabPane.java
 *
 * Created on 28 June 2002, 13:19
 */

package org.owasp.webscarab.ui.swing.spider;
import org.owasp.webscarab.ui.swing.util.Module;
import org.owasp.webscarab.ui.swing.util.AuditRowBean;
import org.owasp.webscarab.ui.swing.EventRouter;
import java.net.URL;
import javax.swing.tree.TreePath;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;

/**
 *
 * @author  thp
 */
public class SpiderConfigTabPane extends javax.swing.JPanel implements Module {

    /** Creates new form SpiderConfigTabPane */
    public SpiderConfigTabPane() {
       initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        _mainPane = new javax.swing.JPanel();
				_mainPane.setLayout( new GridLayout( 1, 1 ) );
        JLabel filler = new JLabel( "hey, ho" );
				filler.setHorizontalAlignment(JLabel.CENTER);
			  _mainPane.add(filler);
    }//GEN-END:initComponents

    private EventRouter _evr = null;
    public void setEventRouter(EventRouter evr){
        _evr = evr;
        if (_evr != null){
            _evr.setModule( this, EventRouter.IDX_SPIDER_CFG );
        }
    }

    /**
     * called to set the running/paused status
     */
    public void setState(boolean run) {
    }    

    /**
     * called to set the progress bar
     */
    public void setProgress(int percent) {
    }    

    /**
     * called with a row from the database that needs displaying
     */
    public void setData( AuditRowBean row ) {
    }
    
    /**
     * called to set the status text on the bottom line of tab pane
     */
    public void setStatusText(String statusLine) {
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel _mainPane;
    // End of variables declaration//GEN-END:variables

}