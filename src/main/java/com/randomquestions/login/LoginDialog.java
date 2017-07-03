package com.randomquestions.login;

import com.randomquestions.config.I18N;
import com.randomquestions.user.UserBean;
import com.randomquestions.user.UserRepository;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class LoginDialog extends JDialog
{
    final static Logger log = Logger.getLogger(LoginDialog.class);

    JLabel jLabelPassword = new JLabel(I18N.lang("loginframe.jLabelPassword"));
    JLabel jLabelUsername = new JLabel(I18N.lang("loginframe.jLabelUsername"));

    JPasswordField jTextFieldPassword = new JPasswordField(40);
    JTextField jTextFieldUsername = new JTextField(40);

    JButton jButtonSave = new JButton(I18N.lang("loginframe.jButtonLogin"));
    JButton jButtonCancel = new JButton(I18N.lang("loginframe.jButtonCancel"));

    /**
     * Constructor to call super first and then init isNew and userToEdit
     * attributes.
     *
     * @param owner
     * @param title
     * @param modal
     */
    public LoginDialog(Frame owner, String title, boolean modal)
    {
        super(owner, title, modal);

        log.debug("START constructor...");


        setLocation(new Random().nextInt(150), new Random().nextInt(150));
        setSize(450, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // new :
        setLayout(new GridLayout(5, 2));
        getContentPane().add(jLabelUsername);
        getContentPane().add(jTextFieldUsername);
        getContentPane().add(jLabelPassword);
        getContentPane().add(jTextFieldPassword);

        getContentPane().add(jButtonSave);
        jButtonSave.addActionListener((ActionEvent ev) ->
        {
            jButtonSaveActionPerformed(ev);
        });

        getContentPane().add(jButtonCancel);
        jButtonCancel.addActionListener((ActionEvent ev) ->
        {
            jButtonCancelActionPerformed(ev);
        });

        log.debug("End of constructor.");

        setVisible(true);
    }

    public void jButtonSaveActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());
        String username = jTextFieldUsername.getText();
        String password = new String(jTextFieldPassword.getPassword());

        UserRepository userRepository = new UserRepository();

        if(userRepository.login(username, password) != null) {
            session.start();
        } else {
            this.dispose();
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        this.dispose();
    }
}
