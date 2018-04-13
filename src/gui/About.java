package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

 public class About extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    About() {
        setIconImage(Toolkit.getDefaultToolkit().getImage
                (About.class.getResource("/resources/wall1.jpg")));
        setAlwaysOnTop(true);
        setResizable(false);
        setBackground(SystemColor.windowBorder);
        setTitle("About Brick Breaker");

        getContentPane().setFont(new Font("Verdana", Font.PLAIN, 17));
        setSize(450, 270);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(SystemColor.menu);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        JTextArea txtAbout = new JTextArea();
        txtAbout.setFont(new Font("Verdana", Font.PLAIN, 17));
        txtAbout.setBackground(SystemColor.menu);
        txtAbout.setEditable(false);
       // contentPanel.add(txtAbout);
        txtAbout.setText("\nBrick Breaker - Copyright © 2018 GPL\n\n"
                + "Authors: Miroslav Statkov & Nedyalko Georgiev\n\n"
                + "Music: Cop\n\n"
                + "Release Date: April 12, 2018\n\n"
                + "Version: 1.0.1 Brick Breaker");
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(SystemColor.menu);
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
