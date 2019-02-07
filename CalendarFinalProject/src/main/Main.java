package main;

import javax.swing.SwingUtilities;

import GUI.CalendarGUI;

public class Main {

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new CalendarGUI();
			}
		});
	}
}
