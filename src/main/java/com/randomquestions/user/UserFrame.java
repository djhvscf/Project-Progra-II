package com.randomquestions.user;

import com.randomquestions.config.I18N;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by djhv9 on 6/22/2017.
 */
public class UserFrame extends JInternalFrame
{
    final static Logger log = Logger.getLogger(UserFrame.class);

    JPanel jPanelHeader = new JPanel();
    JLabel jLabel1 = new JLabel(I18N.lang("userframe.jLabel1"));
    JButton jButtonDelete = new JButton(I18N.lang("userframe.jButtonDelete"));
    JButton jButtonAdd = new JButton(I18N.lang("userframe.jButtonAdd"));
    JButton jButtonEdit = new JButton(I18N.lang("userframe.jButtonEdit"));
    public JTable jTable1;

    /**
     * Constructor.
     */
    public UserFrame()
    {
        log.debug("START constructor...");

        setTitle(I18N.lang("userframe.title"));
        setLocation(new Random().nextInt(100), new Random().nextInt(100));
        setSize(550, 350);
        setVisible(false);
        setClosable(true);
        setIconifiable(true);
        //setMaximizable(false);
        //setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // header :
        jPanelHeader.setBorder(BorderFactory.createTitledBorder(I18N.lang("userframe.jPanelHeader")));

        jPanelHeader.add(jButtonDelete);
        jButtonDelete.addActionListener((ActionEvent ev) ->
        {
            jButtonDeleteActionPerformed(ev);
        });

        jPanelHeader.add(jButtonAdd);
        jButtonAdd.addActionListener((ActionEvent ev) ->
        {
            jButtonAddActionPerformed(ev);
        });

        jPanelHeader.add(jButtonEdit);
        jButtonEdit.addActionListener((ActionEvent ev) ->
        {
            jButtonEditActionPerformed(ev);
        });

        getContentPane().add(jPanelHeader, BorderLayout.NORTH);

        // Table :
        jTable1 = new JTable(this.getData());
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setDefaultEditor(Object.class, null);

        getContentPane().add(new JScrollPane(jTable1), BorderLayout.CENTER);

        log.debug("End of constructor.");
    }

    /**
     * Method to get the data from Repository and return it in DefaultTableModel
     * object. Very useful for refreshing JTable after data modification.
     *
     * @return
     */
    public DefaultTableModel getData()
    {
        log.debug("Start method...");

        // Comumns :
        String[] columns = new String[]
                {
                        I18N.lang("userframe.jtable1.column.id"),
                        I18N.lang("userframe.jtable1.column.name"),
                        I18N.lang("userframe.jtable1.column.username"),
                        I18N.lang("userframe.jtable1.column.email")
                };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // get data rows :
        UserRepository userRepository = new UserRepository();
        ArrayList<UserBean> users = userRepository.findAll();

        // transform ArrayList<> to Object[][] :
        for (UserBean product : users)
        {
            model.addRow(new Object[]
                    {
                            product.getId(),
                            product.getName(),
                            product.getEmail(),
                            product.getUsername()
                    });
        }

        log.debug("End method.");

        return model;
    }

    public void jButtonDeleteActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        log.debug("selectedRowCount : " + jTable1.getSelectedRowCount());

        if (jTable1.getSelectedRowCount() > 0)
        {
            UserRepository userRepository = new UserRepository();
            int[] selectedRows = jTable1.getSelectedRows();
            for (int index = 0; index < selectedRows.length; index++)
            {
                log.debug("Delete row with id=" + jTable1.getValueAt(selectedRows[index], 0));
                userRepository.delete((long) jTable1.getValueAt(selectedRows[index], 0));
            }

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }

    public void jButtonAddActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        new UserDialog(null, I18N.lang("userdialog.addtitle"), true, true, null);

        // refresh the Table Model :
        jTable1.setModel(this.getData());
    }

    public void jButtonEditActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        if (jTable1.getSelectedRowCount() > 0)
        {
            long product_id = (long) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            log.debug("Trying to edit product with id : " + product_id);

            UserRepository userRepository = new UserRepository();

            new UserDialog(null, I18N.lang("userdialog.edittitle"), true, false, userRepository.find(product_id));

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }
}
