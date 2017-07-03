package com.randomquestions.login;

import com.randomquestions.config.I18N;
import com.randomquestions.user.UserDialog;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;


public class LoginFrame extends JInternalFrame
{
    final static Logger log = Logger.getLogger(LoginFrame.class);

    JPanel jPanelHeader = new JPanel();
    JButton jButtonLogin = new JButton(I18N.lang("loginframe.jButtonLogin"));
    JButton jButtonCancel = new JButton(I18N.lang("loginframe.jButtonCancel"));

    /**
     * Constructor.
     */
    public LoginFrame()
    {
        log.debug("START constructor...");

        setTitle(I18N.lang("userframe.title"));
        setLocation(new Random().nextInt(100), new Random().nextInt(100));
        setSize(550, 350);
        setVisible(false);
        setClosable(true);
        setIconifiable(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // header :
        jPanelHeader.setBorder(BorderFactory.createTitledBorder(I18N.lang("userframe.jPanelHeader")));

        jPanelHeader.add(jButtonLogin);
        jButtonLogin.addActionListener((ActionEvent ev) ->
        {
            jButtonLoginActionPerformed(ev);
        });

        jPanelHeader.add(jButtonCancel);
        jButtonCancel.addActionListener((ActionEvent ev) ->
        {
            jButtonCancelActionPerformed(ev);
        });

        getContentPane().add(jPanelHeader, BorderLayout.NORTH);

        log.debug("End of constructor.");
    }

    public void jButtonLoginActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        new LoginDialog(null, I18N.lang("logindialog.logintitle"), true);
    }

    public void jButtonCancelActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());
        this.dispose();
    }
}
