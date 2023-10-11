import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table table;
	
	//Creating the outer frame
	Frame(){
		table = new Table();
		this.add(table);
		this.setTitle("Table Tennis");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
