import java.applet.*;  
import java.awt.*;  
import java.awt.event.*;

public class AnimatedCyberPet extends Applet implements ActionListener {
    private final int PAUSE = 2000000;                     // Named constant
                                                           // Instance variables.
    private CyberPet pet1 = new CyberPet("Socrates");      // CyberPet
    private Label nameLabel = new Label("Hi! My name is "  // Label
             + pet1.getName() + " and currently I am : ");
    private TextField stateField = new TextField(12);      // A TextField
    private Button eatButton = new Button("Eat!");         // Two Buttons
    private Button sleepButton = new Button("Sleep!");   
    private Image eatImg, eat2Img, sleepImg, happyImg;     // Images for animation

    public void init() {  
        eatButton.addActionListener(this);     // Assign the listeners to the buttons.
        sleepButton.addActionListener(this);
        stateField.setText( pet1.getState() ); // Initialize the TextField
        stateField.setEditable(false);
        add(nameLabel);                        // Add the components to the applet.
        add(stateField);
        add(eatButton);
        add(sleepButton);
        eatImg = getImage(getCodeBase(), "eatImage.gif");    // Load the images
        eat2Img = getImage(getCodeBase(), "eat2Image.gif");
        sleepImg = getImage(getCodeBase(), "sleepImage.gif");
        happyImg = getImage(getCodeBase(), "happyImage.gif");
        setSize(300,300);                                    // Set the applet's size
    } // init()

    public void paint(Graphics g) {
        String petState = pet1.getState();
        if (petState.equals("Eating"))
            doEatAnimation(g);
        else if (petState.equals("Sleeping"))
            g.drawImage(sleepImg, 20, 100, this);
    } // paint()
  
    private void doEatAnimation(Graphics g) {
        for (int k = 0; k < 5; k++) {
            g.drawImage( eatImg ,20, 100, this);
            busyWait(PAUSE);
            g.drawImage(eat2Img, 20, 100, this);
            busyWait(PAUSE);        
        }
        g.drawImage(happyImg, 20, 100, this);
    } // doEatAnimation()
  
    private void busyWait(int N) {
        for (int k = 0; k < N; k++) ;   // Empty for body --- does nothing
    } // busyWait()
  
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eatButton)
            pet1.eat();
        else if (e.getSource() == sleepButton)
            pet1.sleep();
        stateField.setText(pet1.getState());
        repaint();
    } // actionPerformed()
} // AnimatedCyberPet
