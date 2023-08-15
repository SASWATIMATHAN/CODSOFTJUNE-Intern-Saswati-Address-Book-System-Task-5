import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;

public class AddressBook extends JFrame implements ActionListener
{
    private final Color
    veryLightGrey = new Color(240, 240, 240),
    darkBlue = new Color(0, 0, 150),
    backGroundColour = veryLightGrey,
    navigationBarColour = Color.lightGray,
    textColour = darkBlue;
    private static final int
    windowWidth = 450, windowHeight = 600,               
    windowLocationX = 200, windowLocationY = 100;        
    private final int
    panelWidth = 450, panelHeight = 250,                 
    leftMargin = 50,                                     
    mainHeadingY = 30,                                   
    detailsY = mainHeadingY+40,                          
    detailsLineSep = 30;                                 
    private final Font
    mainHeadingFont = new Font("SansSerif", Font.BOLD, 20),
    detailsFont = new Font("SansSerif", Font.PLAIN, 14);

    private JButton
    first = new JButton("|<"),            
    previous = new JButton("<"),          
    next = new JButton(">"),              
    last = new JButton(">|");             

    private JButton
    addContact = new JButton("Add new contact"),   
    deleteContact = new JButton("Delete contact"), 
    deleteAll = new JButton("Delete all"),         
    findContact = new JButton("Find exact name"),  
    findPartial = new JButton("Find partial name"),
    sortAtoZ = new JButton("Sort A to Z"),         
    sortZtoA = new JButton("Sort Z to A");         

    private JTextField
    nameField = new JTextField(20),                
    addressField = new JTextField(30),             
    mobileField = new JTextField(12),              
    emailField = new JTextField(30);               

	private JPanel contactDetails = new JPanel()
        {
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g); 
                paintScreen(g);          
            }
        };

    public static void main(String[] args)
    {
        AddressBook contacts = new AddressBook();
        contacts.setSize(windowWidth, windowHeight);
        contacts.setLocation(windowLocationX, windowLocationY);
        contacts.setTitle("ADDRESS BOOK SYSTEM");
        contacts.setUpAddressBook();
        contacts.setUpGUI();
        contacts.setVisible(true);
    } 

    private void setUpAddressBook()
    {
        currentSize = 0;    
        addContact("Saswati", "Liluah, Howrah", "9339596197", "abc@gmail.com");
        currentContact = 0;
    } 

    private void setUpGUI()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        window.setBackground(navigationBarColour);
        window.add(new JLabel("Navigation:"));
        window.add(first);
        first.addActionListener(this);
        window.add(previous);
        previous.addActionListener(this);
        window.add(next);
        next.addActionListener(this);
        window.add(last);
        last.addActionListener(this);

        contactDetails.setPreferredSize(new Dimension(panelWidth, panelHeight));
        contactDetails.setBackground(backGroundColour);
        window.add(contactDetails);

        JPanel addDelPanel = new JPanel();
        addDelPanel.add(addContact);
        addContact.addActionListener(this);
        addDelPanel.add(deleteContact);
        deleteContact.addActionListener(this);
        addDelPanel.add(deleteAll);
        deleteAll.addActionListener(this);
        window.add(addDelPanel);
        JPanel findPanel = new JPanel();
        findPanel.add(findContact);
        findContact.addActionListener(this);
        findPanel.add(findPartial);
        findPartial.addActionListener(this);
        window.add(findPanel);
        JPanel sortPanel = new JPanel();
        sortPanel.add(sortAtoZ);
        sortAtoZ.addActionListener(this);
        sortPanel.add(sortZtoA);
        sortZtoA.addActionListener(this);
        window.add(sortPanel);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("New/find name:"));
        namePanel.add(nameField);
        window.add(namePanel);

        JPanel addressPanel = new JPanel();
        addressPanel.add(new JLabel("New address:"));
        addressPanel.add(addressField);
        window.add(addressPanel);

        JPanel mobilePanel = new JPanel();
        mobilePanel.add(new JLabel("New mobile:"));
        mobilePanel.add(mobileField);
        window.add(mobilePanel);

        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("New email:"));
        emailPanel.add(emailField);
        window.add(emailPanel);
    } 
    private void paintScreen(Graphics g)
    {
        // Main heading
        g.setColor(textColour);
        g.setFont(mainHeadingFont);
        g.drawString("ADDRESS BOOK SYSTEM", leftMargin, mainHeadingY);
        displayCurrentDetails(g);
    } 
    private void displayCurrentDetails(Graphics g)
    {
        g.setColor(textColour);
        g.setFont(detailsFont);
        if (currentContact == -1)           
            g.drawString("There are no contacts", leftMargin, detailsY);
        else
        {   
            g.drawString(name[currentContact], leftMargin, detailsY);
            g.drawString(address[currentContact], leftMargin, detailsY + detailsLineSep);
            g.drawString("Mobile: " + mobile[currentContact], leftMargin, detailsY + 2 * detailsLineSep);
            g.drawString("Email: " + email[currentContact], leftMargin, detailsY + 3 * detailsLineSep);
        }
    } 
    public void actionPerformed(ActionEvent e)
    {
        
        if (e.getSource() == first)
            if (currentContact >= 0)
                currentContact = 0;
            else
                currentContact = -1;

           if (e.getSource() == previous && currentContact > 0)
            currentContact--;

        if (e.getSource() == next && currentContact < currentSize - 1)
            currentContact++;

        if (e.getSource() == last)
            currentContact = currentSize - 1;

        if (e.getSource() == addContact)
            doAddContact();

        if (e.getSource() == deleteContact)
            doDeleteContact();

        if (e.getSource() == deleteAll)
            doDeleteAll();

        if (e.getSource() == findContact)
            doFindContact();

        if (e.getSource() == findPartial)
            doFindPartial();

        if (e.getSource() == sortAtoZ)
            doSortAtoZ();

        if (e.getSource() == sortZtoA)
            doSortZtoA();

        repaint();
    } 
    private void doAddContact()
    {
        String newName = nameField.getText();       nameField.setText("");
        String newAddress = addressField.getText(); addressField.setText("");
        String newMobile = mobileField.getText();   mobileField.setText("");
        String newEmail = emailField.getText();     emailField.setText("");
        if (newName.length() == 0)         
        {
            JOptionPane.showMessageDialog(null, "No name entered");
            return;
        }
        int index = addContact(newName, newAddress, newMobile, newEmail); 
        if (index == -1)                   
            JOptionPane.showMessageDialog(null, "No space for new name");
        else
            currentContact = index;        
    } 
    private void doDeleteContact()
    {
        if (currentSize == 0)               
        {
            JOptionPane.showMessageDialog(null, "No contacts to delete");
            return;
        }
        deleteContact(currentContact);
        if (currentContact == currentSize)    
            currentContact--;                 
    } 
    private void doDeleteAll()
    {
        clearContacts();
        currentContact = -1;    
    } 
    private void doFindContact()
    {
        String searchName = nameField.getText();
        if (searchName.length() == 0)               
        {
            JOptionPane.showMessageDialog(null, "Name must not be empty");
            return;
        }
        int location = findContact(searchName);     
        if (location == -1)                         
            JOptionPane.showMessageDialog(null, "Name not found");
        else
        {
            currentContact = location;              
            nameField.setText("");                  
        }
    } 

    
    private void doFindPartial()
    {
        String searchText = nameField.getText();
        if (searchText.length() == 0)               
        {
            JOptionPane.showMessageDialog(null, "Search text must not be empty");
            return;
        }
        int location = findPartial(searchText);     
        if (location == -1)                         
            JOptionPane.showMessageDialog(null, "Name not found");
        else
        {
            currentContact = location;              
            nameField.setText("");                  
        }
    } 
    private void doSortAtoZ()
    {
        sortAtoZ();
        if (currentSize > 0)
            currentContact = 0;      
        else
            currentContact = -1;
    } 

    private void doSortZtoA()
    {
        sortZtoA();
        if (currentSize > 0)
            currentContact = 0;      
        else
            currentContact = -1;
    } 

    private final int databaseSize = 10;
    private String[]
    name = new String[databaseSize],
    address = new String[databaseSize],
    mobile = new String[databaseSize],
    email = new String[databaseSize];

     private int currentSize = 0;

     private int currentContact = -1;
    private int addContact(String newName, String newAddress, String newMobile, String newEmail)
    {
                
        name[currentSize] = newName;         
        address[currentSize] = newAddress;
        mobile[currentSize] = newMobile;
        email[currentSize] = newEmail;
        currentSize++;                       
        return currentSize-1;                
    }
    
    private void deleteContact(int index)
    {
    	if (index > -1)
    	{   
            for (int i = index; i < currentSize; i++)
            {
            	name[i] = name[i+1];         
                address[i] = address[i+1];
                mobile[i] = mobile[i+1];
                email[i] = email[i+1];
            }
            
            currentSize--;

    	} else return;
    } 

   
    private void clearContacts()
    {
    	for (int i = 0; i < currentSize; i++)
    	{
    		name[i] = null;         
            address[i] = null;
            mobile[i] = null;
            email[i] = null;
            
            currentSize--;
    	}
    } 
    private int findContact(String searchName)
    {
    	for (int i = 0; i < currentSize; i++)
    	{
    		if (name[i].equals(searchName))
    			return i;
    	}
    	
        return -1;                          
    } 
    private int findPartial(String searchText)
    {
    	for (int i = 0; i < currentSize; i++)
    	{
    		if (name[i].contains(searchText))
    			return i;
    	}
        return -1;                          
    } 
    private void sortAtoZ()
    {
    }

    private void sortZtoA()
    {
    }

}