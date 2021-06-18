import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Address extends JPanel {
	private JTextField tName, tAddress, tPhone, tEmail;
	private JButton bSave, bSearch, bUpdate, bDelete, bClear;
	private String name, address, email, phone;
	private String file;
	private int gxb = 0, gyb = 0;
	private int gxt = 1, gyt = 0;
	private static final long serialVersionUID = -3017242459715155066L;
	private PersonDatab pd;
	private int selId = 0;
	private ArrayList<PersonInfo> personList;

	public Address() {
		name = "";
		address = "";
		email = "";
		phone = "0";
		file = "customers.accdb";
		pd = new PersonDatab(file);
		personList = new ArrayList<PersonInfo>();
		setBackground(Color.gray);
		setBorder(new EmptyBorder(30, 10, 30, 10));
		setLayout(new GridBagLayout());
		addComponents();

	}

	private void createButton(String s) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gxb;
		gbc.gridy = gyb;
		gbc.insets = new Insets(5, 5, 5, 5);
		JButton b = new JButton(s);
		add(b, gbc);
		gyb++;
	}

	private JTextField createText() {
		JTextField t = new JTextField(20);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gxt;
		gbc.gridy = gyt;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(t, gbc);
		gyt++;
		return t;

	}

	private void addComponents() {
		createButton("Nume");
		tName = createText();

		createButton("Adresa");
		tAddress = createText();

		createButton("Telefon");
		tPhone = createText();

		createButton("Email");
		tEmail = createText();

		bSave = new JButton("Save");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = ++gyb;
		gbc.gridx = 0;
		add(bSave, gbc);

		bSearch = new JButton("Search name");
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridy = gyb;
		gbc2.gridx = 1;
		add(bSearch, gbc2);

		bUpdate = new JButton("Update");
		bUpdate.setEnabled(false);
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridy = gyb;
		gbc3.gridx = 2;
		add(bUpdate, gbc3);

		bDelete = new JButton("Delete");
		bDelete.setEnabled(false);
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridy = gyb;
		gbc4.gridx = 3;
		add(bDelete, gbc4);

		bClear = new JButton("Clear");
		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.gridy = ++gyb;
		gbc5.gridx = 1;
		gbc5.gridwidth = 2;
		add(bClear, gbc5);

		// add functionality for buttons:
		bSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});

		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});

	}

	public void save() {
		name = tName.getText();
		name = name.toUpperCase();
		address = tAddress.getText();
		email = tEmail.getText();
		phone = tPhone.getText();
		boolean w = true;
		try {
			@SuppressWarnings("unused")
			long nr = Long.parseLong(phone);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null, "Insert a correct phone number");
			w = false;

		}
		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "Insert a name");
			w = false;
		}
		if (w) {
			PersonInfo p = new PersonInfo(name, address, phone, email);
			pd.saveP(p);
			clear();
		}
	}

	public void search() {
		name = tName.getText();
		name = name.toUpperCase();
		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "Insert a name to search");
		} else {
			personList.clear();
			personList = pd.searchP(name); // keeps reference
			if (personList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No records found");
			} else {
				bUpdate.setEnabled(true);
				bDelete.setEnabled(true);
				Object[] selectionValues = personList.toArray();
				PersonInfo p0 = (PersonInfo) personList.get(0);
				Object selection = JOptionPane
						.showInputDialog(null, "Search results:", "Contacts",
								JOptionPane.QUESTION_MESSAGE, null,
								selectionValues, p0);
				PersonInfo sel = (PersonInfo) selection;
				System.out.println(sel);
				tName.setText(sel.getName());
				tAddress.setText(sel.getAddress());
				tPhone.setText(sel.getPhone());
				tEmail.setText(sel.getEmail());
				selId = sel.getId();
			}
		}
	}

	public void update() {
		if (selId != 0) {
			PersonInfo p = new PersonInfo(tName.getText(), tAddress.getText(),
					tPhone.getText(), tEmail.getText());
			pd.updateP(p, selId);
			JOptionPane.showMessageDialog(null, "Record was updated");
			bUpdate.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "Press clear and try again");
		}
	}

	public void delete() {
		if (selId != 0) {
			pd.deleteP(selId);
			JOptionPane.showMessageDialog(null, "Record was deleted");
			clear();
			bUpdate.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "Press clear and try again");
		}

	}

	public void clear() {
		tName.setText("");
		tAddress.setText("");
		tPhone.setText("");
		tEmail.setText("");
		personList.clear();
	}

}
