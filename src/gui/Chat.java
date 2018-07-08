package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import networking.Client;
import networking.Server;

public class Chat extends JFrame implements ActionListener {
	JPanel panel = new JPanel();
	JLabel chatThread = new JLabel();
	JTextField messageField = new JTextField(20);
	JButton send = new JButton("send");
	
	Server server;
	Client client;
	
	public static void main(String[] args) {
		new Chat();
	}
	
	public  Chat() {
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Hosting", JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
			server = new Server(8080, chatThread);
			setTitle("SERVER");
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			send.addActionListener((e)->{
				server.sendMessage(messageField.getText());
				chatThread.setText(chatThread.getText() + messageField.getText());
				messageField.setText("");
			});
			add(panel);
			panel.setLayout(new BorderLayout());
			panel.add(chatThread, BorderLayout.CENTER);
			panel.add(messageField, BorderLayout.LINE_END);
			panel.add(send, BorderLayout.LINE_START);
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			server.start();
			
		} else {
			setTitle("CLIENT");
			String ipStr = JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			client = new Client(ipStr, port, chatThread);
			send.addActionListener((e)->{
				client.sendMessage(messageField.getText());
				chatThread.setText(chatThread.getText() + messageField.getText());
				messageField.setText("");
			});
			add(panel);
			panel.add(chatThread);
			panel.add(messageField);
			panel.add(send);
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
