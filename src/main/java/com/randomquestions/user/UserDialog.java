package com.randomquestions.user;

import com.randomquestions.config.I18N;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class UserDialog extends JDialog
{
    final static Logger log = Logger.getLogger(UserDialog.class);

    JLabel jLabelId = new JLabel(I18N.lang("userframe.jLabelId"));
    JLabel jLabelName = new JLabel(I18N.lang("userframe.jLabelName"));
    JLabel jLabelEmail = new JLabel(I18N.lang("userframe.jLabelEmail"));
    JLabel jLabelPassword = new JLabel(I18N.lang("userframe.jLabelPassword"));
    JLabel jLabelUsername = new JLabel(I18N.lang("userframe.jLabelUsername"));

    JTextField jTextFieldId = new JTextField(40);
    JTextField jTextFieldName = new JTextField(40);
    JTextField jTextFieldEmail = new JTextField(40);
    JPasswordField jTextFieldPassword = new JPasswordField(40);
    JTextField jTextFieldUsername = new JTextField(40);

    JButton jButtonSave = new JButton(I18N.lang("userframe.jButtonSave"));
    JButton jButtonCancel = new JButton(I18N.lang("userframe.jButtonCancel"));

    boolean isNew;
    UserBean userToEdit;

    /**
     * Constructor to call super first and then init isNew and userToEdit
     * attributes.
     *
     * @param owner
     * @param title
     * @param modal
     * @param isNew
     * @param userToEdit
     */
    public UserDialog(Frame owner, String title, boolean modal, boolean isNew, UserBean userToEdit)
    {
        super(owner, title, modal);

        log.debug("START constructor...");

        // set this param globals to use in other methods :
        this.isNew = isNew;
        this.userToEdit = userToEdit;

        setLocation(new Random().nextInt(150), new Random().nextInt(150));
        setSize(450, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // new :
        if (isNew)
        {
            setLayout(new GridLayout(5, 2));
        } // modification :
        else
        {
            setLayout(new GridLayout(5, 2));
            getContentPane().add(jLabelId);
            getContentPane().add(jTextFieldId);
            jTextFieldId.setEditable(false);

            // write values in form :
            jTextFieldId.setText("" + userToEdit.getId());
            jTextFieldName.setText(userToEdit.getName());
            jTextFieldUsername.setText(userToEdit.getUsername());
            jTextFieldEmail.setText(userToEdit.getEmail());
        }

        getContentPane().add(jLabelName);
        getContentPane().add(jTextFieldName);

        getContentPane().add(jLabelEmail);
        getContentPane().add(jTextFieldEmail);

        getContentPane().add(jLabelUsername);
        getContentPane().add(jTextFieldUsername);

        if(isNew) {
            getContentPane().add(jLabelPassword);
            getContentPane().add(jTextFieldPassword);
        }

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

        UserRepository userRepository = new UserRepository();
        UserBean userBean = new UserBean();

        userBean.setName(jTextFieldName.getText());
        userBean.setUsername(jTextFieldUsername.getText());
        userBean.setEmail(jTextFieldEmail.getText());

        if (this.isNew)
        {
            userBean.setPassword(jTextFieldPassword.getPassword().toString());

            if (userRepository.create(userBean) != null)
            {
                this.dispose();
            }
        } else
        {
            userBean.setId(this.userToEdit.getId());
            if (userRepository.update(userBean) != null)
            {
                this.dispose();
            }
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        this.dispose();
    }
}
