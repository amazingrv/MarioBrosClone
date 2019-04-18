package com.mygdx.mariobrosclone.desktop;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Menu {
	JFrame frame;
	ActionListener l;
	boolean flag;

	public boolean createMenu() {
		flag = false;
		frame = new JFrame("JToolBar Demo");
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(200, 100);

		Button start = new Button("Start");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				flag = true;

			}
		});
		frame.add(start);
		frame.setVisible(true);
		if (flag)
			return true;
		return false;
	}

}
