package View;





import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.apache.lucene.queryparser.classic.ParseException;

import com.opencsv.exceptions.CsvException;

import Controller.Controller;


public class GUI {
    private  JFrame f;
	private  Controller controller;
	
	private  JLabel searchLabel;
	private  JLabel incorrectLabel;
	private  JLabel sortByLabel;
	private  JTextField queryTextField;
	
	private  JButton searchButton;
	private  JButton previousButton;
	private  JButton nextButton ;
	private  ArrayList<JButton> resultBtns;
	private  JRadioButton titleRadioBtn;
	private  JRadioButton releaseYearRadioBtn;
	
	private  JPanel upperPanel;
	private  JPanel searchPanel;
	private  JPanel sortPanel;
	private  JPanel correctSpellingPanel;
	private  JPanel resultsPanel;
	
	public GUI() throws IOException, CsvException {
		initController();
		initWindow();
		initializeSearchPanel();
		initializeSortPanel();
		initializeCorrectSpellingPanel();
		initializeUpperPanel();
		initializeResultsPanel();
		addButtonActionListeners();
		upperPanel.add(searchPanel);
		upperPanel.add(sortPanel);
		upperPanel.add(correctSpellingPanel);

		f.add(upperPanel);
		f.add(resultsPanel);
		f.setVisible(true);
		
		
		
		
		
	}
	
	
	
	
	private  void initController()  throws IOException, CsvException {
		controller=new Controller();
	}
	
	private  void initWindow() {
		f=new JFrame();
		f.setTitle("Movies Search Engine");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024,768);
		f.setResizable(false);
		f.setLayout(new GridLayout(2,1));


	}
	private  void initializeUpperPanel() {
		upperPanel=new JPanel();
		upperPanel.setLayout(new GridLayout(3,1));

		
	}
	
	private  void initializeSearchPanel() {
		searchPanel= new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		initializeSearchPanelComponents();
		searchPanel.add(searchLabel);
		searchPanel.add(queryTextField);
		searchPanel.add(searchButton);
		searchPanel.add(previousButton);
		searchPanel.add(nextButton);
	}
	
	private  void initializeSearchPanelComponents() {
		
			searchLabel = new JLabel("Lucene Search Engine");
			
			queryTextField= new JTextField();
			queryTextField.setPreferredSize(new Dimension(400,20));
			searchButton = new JButton("Search");
			previousButton = new JButton("Previous Page");
			nextButton = new JButton("Next Page");
			previousButton.setVisible(false);
			nextButton.setVisible(false);
	}
	
	
	private  void initializeSortPanel() {
		sortPanel=new JPanel();
		sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		initializeSortPanelComponents();
		sortPanel.add(sortByLabel);
		sortPanel.add(titleRadioBtn);
		sortPanel.add(releaseYearRadioBtn);
	}
	
	private  void initializeSortPanelComponents() {
		sortByLabel=new JLabel("Sort By:");
		titleRadioBtn=new JRadioButton("title");
		releaseYearRadioBtn= new JRadioButton("release year");
		ButtonGroup group= new ButtonGroup();
		group.add(titleRadioBtn);
		group.add(releaseYearRadioBtn);
	}

	
	

	private  void initializeCorrectSpellingPanel() {
		correctSpellingPanel= new JPanel();
		correctSpellingPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		initializeCorrectSpellingPanelComponents();
		correctSpellingPanel.add(incorrectLabel);

	}
	
	private  void initializeCorrectSpellingPanelComponents() {
		incorrectLabel= new JLabel("Did you mean :");
		incorrectLabel.setVisible(false);
	}
		
	
	
	private  void initializeResultsPanel() {
		resultsPanel= new JPanel();
		resultsPanel.setLayout(new GridLayout(10,1,10,10));
		initializeResultBtns();
		
	}
	
	private  void initializeResultBtns() {
		resultBtns=new ArrayList<JButton>();
		for(int i=0;i<10;i++) {
			JButton resultButton= new JButton("");
			resultButton.setVisible(false);
			resultBtns.add(resultButton);
			resultsPanel.add(resultButton);
		}
	}
	private  void disableResultBtns() {
		for(JButton resultBtn :resultBtns) {
			resultBtn.setVisible(false);
		}
	}
	

	private  void addButtonActionListeners() {
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					disableResultBtns();
					nextButton.setVisible(false);
					controller.search(queryTextField.getText());
					controller.setFirstSearchResultBtnsText(resultBtns);
					
					
					if(controller.checkForNextRemainingResults()) {
						nextButton.setVisible(true);
					}
				
					
				} catch (ParseException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		});
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButton.setVisible(true);
				disableResultBtns();
				controller.setNextSearchResultBtnsText(resultBtns);
				
				if(!controller.checkForNextRemainingResults()) {
					nextButton.setVisible(false);
				}
				
					
			} 
		});
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableResultBtns();
				controller.setPreviousSearchResultBtnsText(resultBtns);
				
				if(!controller.checkForPreviousRemainingResults()) {
					previousButton.setVisible(false);
				}
				if(controller.checkForNextRemainingResults()) {
					nextButton.setVisible(true);
				}
				
				
					
			} 
			
			
		});
		
		titleRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButton.setVisible(false);
				disableResultBtns();
				
				
				controller.sortByTitle(resultBtns);;

				if(controller.checkForNextRemainingResults()) {
					nextButton.setVisible(true);
				}
				
				
					
			} 
		});
		
		releaseYearRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButton.setVisible(false);
				disableResultBtns();
				
				
				controller.sortByYear(resultBtns);;

				if(controller.checkForNextRemainingResults()) {
					nextButton.setVisible(true);
				}
				
				
					
			} 
		});
	}
	

	


	public static  void main(String[] args) throws IOException, CsvException{
			new GUI();

	}
	

}
