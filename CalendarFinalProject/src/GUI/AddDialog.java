package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import toDo.EmptyToDoException;
import toDo.TimeInputException;
import toDo.ToDo;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class AddDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEventName;
	private JSpinner spFrom;
	private JSpinner spTo;
	private JTextField txtFrom;
	private JTextField txtTo;
	private String fileName = "TodoData/" + CalendarGUI.calYear + ((CalendarGUI.calMonth + 1) < 10 ? "0" : "")
			+ (CalendarGUI.calMonth + 1) + (CalendarGUI.calDayOfMon < 10 ? "0" : "") + CalendarGUI.calDayOfMon
			+ ".dat";
	private String todo;
	private int fromH;
	private int fromM;
	private int toH;
	private int toM;

	/**
	 * Create the dialog.
	 */
	public AddDialog(MemoDialog parent, int flag) {
		super(parent, true);
		setBounds(100, 100, 361, 255);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		setTitle(CalendarGUI.MONTH_NAME[CalendarGUI.calMonth - 1] + " - " + CalendarGUI.calDayOfMon);

		gbl_contentPanel.columnWeights = new double[] { 1.0, 2.0 };
		gbl_contentPanel.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblEvent = new JLabel("Event");
			GridBagConstraints gbc_lblEvent = new GridBagConstraints();
			gbc_lblEvent.fill = GridBagConstraints.VERTICAL;
			gbc_lblEvent.insets = new Insets(0, 0, 5, 5);
			gbc_lblEvent.gridx = 0;
			gbc_lblEvent.gridy = 0;
			contentPanel.add(lblEvent, gbc_lblEvent);
		}
		{
			txtEventName = new JTextField();

			if (flag == 1) {
				try {
					File f = new File(fileName);
					if (f.exists()) {
						ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
						LinkedList<ToDo> tmp = (LinkedList<ToDo>) is.readObject();
						is.close();
						todo = tmp.get(parent.getIndex()).getTodo();
						fromH = tmp.get(parent.getIndex()).getFromHour();
						fromM = tmp.get(parent.getIndex()).getFromMinute();
						toH = tmp.get(parent.getIndex()).getToHour();
						toM = tmp.get(parent.getIndex()).getToMinute();
						if(fromH>12) fromH-=12;
						else fromH+=12;
						if(toH>12) toH-=12;
						else toH+=12;
						tmp.clear();
						txtEventName.setText(todo);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				JLabel lblEventNamePlaceholder = new JLabel("Enter event name");
				lblEventNamePlaceholder.setForeground(Color.GRAY);
				txtEventName.setLayout(new FlowLayout());
				txtEventName.add(lblEventNamePlaceholder);
				txtEventName.addKeyListener(new KeyAdapter() {

					@Override
					public void keyTyped(KeyEvent e) {
						if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
							lblEventNamePlaceholder.setVisible(false);
						} else {
							if (txtEventName.getText().equals(""))
								lblEventNamePlaceholder.setVisible(true);
						}
					}

				});
			}
			txtEventName.setToolTipText("Enter event name");
			GridBagConstraints gbc_txtEventName = new GridBagConstraints();
			gbc_txtEventName.fill = GridBagConstraints.BOTH;
			gbc_txtEventName.insets = new Insets(0, 0, 5, 0);
			gbc_txtEventName.gridx = 1;
			gbc_txtEventName.gridy = 0;
			contentPanel.add(txtEventName, gbc_txtEventName);
			txtEventName.setColumns(10);

		}

		{
			JLabel lblFrom = new JLabel("From");
			GridBagConstraints gbc_lblFrom = new GridBagConstraints();
			gbc_lblFrom.fill = GridBagConstraints.VERTICAL;
			gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
			gbc_lblFrom.gridx = 0;
			gbc_lblFrom.gridy = 1;
			contentPanel.add(lblFrom, gbc_lblFrom);
		}
		{
			txtFrom = new JTextField();
			txtFrom.setLayout(new BorderLayout());
			txtFrom.setToolTipText("Enter hour:minnute");
			GridBagConstraints gbc_txtFrom = new GridBagConstraints();
			gbc_txtFrom.fill = GridBagConstraints.BOTH;
			gbc_txtFrom.insets = new Insets(0, 0, 5, 0);
			gbc_txtFrom.gridx = 1;
			gbc_txtFrom.gridy = 1;
			contentPanel.add(txtFrom, gbc_txtFrom);
			txtFrom.setColumns(10);

			spFrom = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor spinnerEditor = new JSpinner.DateEditor(spFrom, "HH:mm");
			spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
			spFrom.setEditor(spinnerEditor);
			spFrom.setValue(new Date(11));

			if (flag == 1) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR, fromH);
				cal.set(Calendar.MINUTE, fromM);
				Date date = cal.getTime();
				spFrom.setValue(date);
			}

			txtFrom.add(spFrom);
		}
		{
			JLabel lblTo = new JLabel("To");
			GridBagConstraints gbc_lblTo = new GridBagConstraints();
			gbc_lblTo.fill = GridBagConstraints.VERTICAL;
			gbc_lblTo.insets = new Insets(0, 0, 5, 5);
			gbc_lblTo.gridx = 0;
			gbc_lblTo.gridy = 2;
			contentPanel.add(lblTo, gbc_lblTo);
		}
		{
			txtTo = new JTextField();
			txtTo.setLayout(new BorderLayout());

			spTo = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor spinnerEditor = new JSpinner.DateEditor(spTo, "HH:mm");
			spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
			spTo.setEditor(spinnerEditor);
			spTo.setValue(new Date(11));
			txtTo.add(spTo);
			
			if (flag == 1) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR, toH);
				cal.set(Calendar.MINUTE, toM);
				Date date = cal.getTime();
				spTo.setValue(date);
			}


			txtTo.setToolTipText("Enter hour:minute");
			GridBagConstraints gbc_txtTo = new GridBagConstraints();
			gbc_txtTo.insets = new Insets(0, 0, 5, 0);
			gbc_txtTo.fill = GridBagConstraints.BOTH;
			gbc_txtTo.gridx = 1;
			gbc_txtTo.gridy = 2;
			contentPanel.add(txtTo, gbc_txtTo);
			txtTo.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if(flag==1) {
							parent.deleteData();	
							
						}

						Date time = (Date) spFrom.getValue();
						Calendar calendar = GregorianCalendar.getInstance();
						calendar.setTime(time);

						String event = txtEventName.getText();

						int startHour = calendar.get(Calendar.HOUR);
						int startMinute = calendar.get(Calendar.MINUTE);
						int amPm = calendar.get(Calendar.AM_PM);
						if (amPm == 1)
							startHour += 12;

						time = (Date) spTo.getValue();
						calendar.setTime(time);

						int endHour = calendar.get(Calendar.HOUR);
						int endMinute = calendar.get(Calendar.MINUTE);
						amPm = calendar.get(Calendar.AM_PM);
						if (amPm == 1)
							endHour += 12;

						File f = new File("TodoData");
						if (!f.isDirectory())
							f.mkdir();

						File file = new File(fileName);
						boolean hasSameTime = false;
						try {

							// if the day has no event
							if (!file.exists()) {
								// save the data to file
								ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
								LinkedList<ToDo> tmp = new LinkedList<ToDo>();
								tmp.add(new ToDo(event, startHour, startMinute, endHour, endMinute));
								os.writeObject(tmp);
								tmp.clear();
								os.close();
								dispose();
							}
							// if the day has at least one event
							else {
								// read ToDo class from file and save to object tmp
								ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
								LinkedList<ToDo> tmp = (LinkedList<ToDo>) is.readObject();

								// add new event to object tmp
								tmp.add(new ToDo(event, startHour, startMinute, endHour, endMinute));

								// sort events by time
								Collections.sort(tmp);

								// Check for events that have the same start and end times
								for (int i = 0; i < tmp.size() - 1; i++) {
									if (tmp.get(i).isSame(tmp.get(i + 1))) {

										JOptionPane.showMessageDialog(null,
												"You have events that have the same time\nPlease change the time",
												"Wrong time", JOptionPane.ERROR_MESSAGE);
										setVisible(true);
										txtFrom.requestFocusInWindow();
										hasSameTime = true;
										break;
									}
								}
								is.close();

								if (!hasSameTime) {
									ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
									os.writeObject(tmp);
									os.close();
									dispose();
								}
								tmp.clear();

							}

						} catch (EmptyToDoException e1) {
							JOptionPane.showMessageDialog(null, "Please enter event name", "empty event name",
									JOptionPane.ERROR_MESSAGE);
							setVisible(true);
							txtEventName.requestFocusInWindow();

						} catch (TimeInputException e1) {
							JOptionPane.showMessageDialog(null, "Start time should be earlier than end time",
									"Wrong time", JOptionPane.ERROR_MESSAGE);
							setVisible(true);
							txtFrom.requestFocusInWindow();

						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
