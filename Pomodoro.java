import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Pomodoro implements ActionListener{
	JFrame frame = new JFrame();
	JButton startButton = new JButton("START");
	JButton resetButton = new JButton("RESET");
	JLabel timeLabel = new JLabel();
	JLabel task = new JLabel();
	int pomodoros = 0;
	int elapsedTime = 1500000;
	int seconds = 0;
	int minutes = 25;
	int hours = 0;
	boolean started = false;
	String seconds_string = String.format("%02d", seconds);
	String minutes_string = String.format("%02d", minutes);
	String hours_string = String.format("%02d", minutes);
	
	Timer timer = new Timer(1000, new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			
			elapsedTime -= 1000;
			minutes = (elapsedTime/60000) % 60;
			seconds = (elapsedTime/1000) % 60;
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);
			timeLabel.setText(minutes_string + ":" + seconds_string);
			if(elapsedTime == 0) {
				pomodoroReset();
			}
		}
		
	});

	Pomodoro(){
		
		task.setText("STUDY TIME");
		task.setBounds(100, 50, 200, 50);
		task.setFont(new Font("Verdana", Font.PLAIN, 20));
		task.setBorder(BorderFactory.createBevelBorder(1));
		task.setOpaque(true);
		task.setHorizontalAlignment(JTextField.CENTER);
		
		timeLabel.setText(minutes_string + ":" + seconds_string);
		timeLabel.setBounds(100, 100, 200, 100);
		timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
		timeLabel.setBorder(BorderFactory.createBevelBorder(1));
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(JTextField.CENTER);
		
		startButton.setBounds(100, 200, 100, 50);
		startButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		
		resetButton.setBounds(200, 200, 100, 50);
		resetButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		resetButton.setFocusable(false);
		resetButton.addActionListener(this);
		
		frame.add(task);
		frame.add(timeLabel);
		frame.add(startButton);
		frame.add(resetButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton) {
			start();
			if(!started) {
				started = true;
				startButton.setText("STOP");
				start();
			}
			else {
				started = false;
				startButton.setText("START");
				stop();
			}
		}
		if(e.getSource() == resetButton) {
			started = false;
			startButton.setText("START");
			reset();
		}
	}
	
	void start() {
		timer.start();
	}
	
	void stop() {
		timer.stop();
	}
	
	void reset() { 
		timer.stop();
		pomodoros = 0;
		elapsedTime = 1500000;
		seconds = 0;
		minutes = 25;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		timeLabel.setText(minutes_string + ":" + seconds_string);
		task.setText("STUDY TIME");
	}
	void pomodoroReset() {
			pomodoros++;
			if(pomodoros % 8 == 0 && pomodoros > 0) {
				elapsedTime = 900000;
				seconds = 0;
				minutes = 15;
				pomodoros = -1;
				seconds_string = String.format("%02d", seconds);
				minutes_string = String.format("%02d", minutes);
				timeLabel.setText(minutes_string + ":" + seconds_string);
				task.setText("BREAK TIME");
				this.infoBox("It's time to take a break", "reset");
			}
			else if(pomodoros % 2 == 0) {
				elapsedTime = 1500000;
				seconds = 0;
				minutes = 25;
				seconds_string = String.format("%02d", seconds);
				minutes_string = String.format("%02d", minutes);
				timeLabel.setText(minutes_string + ":" + seconds_string);
				task.setText("STUDY TIME");
				this.infoBox("It's time to study again", "reset");
			}
			else {
				elapsedTime = 300000;
				seconds = 0;
				minutes = 5;
				seconds_string = String.format("%02d", seconds);
				minutes_string = String.format("%02d", minutes);
				timeLabel.setText(minutes_string + ":" + seconds_string);
				task.setText("BREAK TIME");
				this.infoBox("It's time to take a break", "reset");
			}
	}

	void infoBox(String infoMessage, String titleBar) {
		
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
}
