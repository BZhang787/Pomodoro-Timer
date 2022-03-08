import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.TrayIcon.MessageType;
public class Pomodoro implements ActionListener{
		JFrame frame = new JFrame();
	JButton startButton = new JButton("START");
	JButton resetButton = new JButton("RESET");
	JButton timeIncrease = new JButton("+1:00");
	JButton timeDecrease = new JButton("-1:00");
	JLabel timeLabel = new JLabel();
	JLabel task = new JLabel();
	int pomodoros = 0;
	int elapsedTime = 1500000;
	int seconds = 0;
	int defaultMinutes = 25;
	int minutes;
	boolean started = false;
	String seconds_string = String.format("%02d", seconds);
	String minutes_string = String.format("%02d", defaultMinutes);
	
	Timer timer = new Timer(1000, new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			
			elapsedTime -= 1000;
			minutes = (elapsedTime/60000);
			seconds = (elapsedTime/1000) % 60;
			timeLabel.setText(formatTime(seconds, minutes));
			if(elapsedTime <= 0) {
				try {
					pomodoroReset();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	});

	Pomodoro(){
		
		task.setText("STUDY TIME");
		task.setBounds(0, 10, 420, 50);
		task.setFont(new Font("Verdana", Font.PLAIN, 25));
		task.setOpaque(true);
		task.setHorizontalAlignment(JTextField.CENTER);
		
		timeLabel.setText(minutes_string + ":" + seconds_string);
		timeLabel.setBounds(0, 60, 420, 150);
		timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(JTextField.CENTER);
		
		startButton.setBounds(0, 250, 105, 50);
		startButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		
		resetButton.setBounds(105, 250, 105, 50);
		resetButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		resetButton.setFocusable(false);
		resetButton.addActionListener(this);
		
		timeIncrease.setBounds(210, 250, 105, 50);
		timeIncrease.setFont(new Font("Verdana", Font.PLAIN, 15));
		timeIncrease.setFocusable(false);
		timeIncrease.addActionListener(this);
		
		timeDecrease.setBounds(315, 250, 105, 50);
		timeDecrease.setFont(new Font("Verdana", Font.PLAIN, 15));
		timeDecrease.setFocusable(false);
		timeDecrease.addActionListener(this);
		
		frame.add(task);
		frame.add(timeLabel);
		frame.add(startButton);
		frame.add(resetButton);
		frame.add(timeIncrease);
		frame.add(timeDecrease);
		
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
		if(e.getSource() == timeIncrease && started == false) {
			elapsedTime += 60000;
			this.defaultMinutes++;
			minutes = (elapsedTime/60000);
			seconds = (elapsedTime/1000) % 60;
			timeLabel.setText(formatTime(seconds, minutes));
		}
		if(e.getSource() == timeDecrease && started == false) {
			if(elapsedTime <= 60000) {
				elapsedTime = 0;
				this.defaultMinutes = 0;
			}else {
				elapsedTime -= 60000;
				this.defaultMinutes--;
			}
			minutes = (elapsedTime/60000);
			seconds = (elapsedTime/1000) % 60;
			timeLabel.setText(formatTime(seconds, minutes));
			
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
		elapsedTime = defaultMinutes * 60000;
		seconds = 0;
		minutes = defaultMinutes;
		timeLabel.setText(formatTime(seconds, minutes));
		task.setText("STUDY TIME");
	}
	void pomodoroReset() throws AWTException {
			pomodoros++;
			if(pomodoros % 8 == 0 && pomodoros > 0) {
				elapsedTime = 900000;
				seconds = 0;
				minutes = 15;
				pomodoros = -1;
				timeLabel.setText(formatTime(seconds, minutes));
				task.setText("BREAK TIME");
				this.infoBox("It's time to take a break", "15 minutes break");
			}
			else if(pomodoros % 2 == 0) {
				elapsedTime = 60000*defaultMinutes;
				seconds = 0;
				minutes = defaultMinutes;
				timeLabel.setText(formatTime(seconds, minutes));
				task.setText("STUDY TIME");
				this.infoBox("It's time to study again", minutes + " minutes studying");
			}
			else {
				elapsedTime = 300000;
				seconds = 0;
				minutes = 5;
				timeLabel.setText(formatTime(seconds, minutes));
				task.setText("BREAK TIME");
				this.infoBox("It's time to take a break", "5 minutes break");
			}
	}
	
	String formatTime(int seconds, int minutes) {
		String seconds_string = String.format("%02d", seconds);
		String minutes_string = String.format("%02d", minutes);
		return (minutes_string + ":" + seconds_string);
	}

	void infoBox(String infoMessage, String titleBar) throws AWTException {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("PomodoroPic.jpg");
		TrayIcon trayIcon = new TrayIcon(image, "Timer Up");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Pomodoro Timer");
		tray.add(trayIcon);
		
		trayIcon.displayMessage(infoMessage, titleBar, MessageType.INFO);
		
	}
}
