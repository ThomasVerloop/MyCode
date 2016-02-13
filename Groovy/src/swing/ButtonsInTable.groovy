package swing
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.log4j.PropertyConfigurator;

import layout.TableLayout;
import helpers.*;

// (ThV) The old way not beeing used ...
WindowListener listener = new WindowAdapter() {
		public void windowClosing (WindowEvent e) { System.exit(0); }
	}

class myListener implements ActionListener{
	static SelectorCaller caller = new SelectorCaller();
	int n;
	public myListener(int i){ n = i }
	public void actionPerformed(ActionEvent e){
		if(n == 7){
			caller.selectFile()
	//		components.FileChooserDemo2.main("NoExit")
		} else{
		  println "Someone pressed button $n, why?"
		}
	}
}

    protected static JFrame showTableWindow (){
        JFrame frame = new JFrame("TableLayout");
        frame.setFont(new Font("Helvetica", Font.PLAIN, 14));
        
        // Set layout
        double f = TableLayout.FILL;
        double p = TableLayout.PREFERRED;
        def sizes = [[f, f, f, f], [p, p, p, p, f]];
        TableLayout layout = new TableLayout(sizes);
        frame.setLayout (layout);
        
        // Create buttons labeled Button1 to Button10
        int numButton = 10;
        
		def button = new JButton[numButton + 1];
        
        for (int i = 1; i <= numButton; i++){
            button[i] = new JButton("Button" + i);
            button[i].addActionListener(new myListener(i));
        }
        // Add the buttons
        frame.add (button[1], "0, 0");
        frame.add (button[2], "1, 0");
        frame.add (button[3], "2, 0");
        frame.add (button[4], "3, 0");
        frame.add (button[5], "0, 1, 3, 1");
        frame.add (button[6], "0, 2, 2, 2");
        frame.add (button[7], "3, 2, 3, 2");
        frame.add (button[8], "0, 3, 0, 4");
        frame.add (button[9], "1, 3, 3, 3");
        frame.add (button[10], "1, 4, 3, 4");

        // Show frame
        frame.pack();
        frame.setLocation (400, 10);
        frame.setVisible(true);

        return frame;
    }
	
PropertyConfigurator.configure("log4j-configuration-file.txt");
	
frame = showTableWindow();
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//frame.addWindowListener (listener);


