package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.apache.lucene.queryparser.classic.ParseException;

import com.opencsv.exceptions.CsvException;

import controller.Controller;


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
	private  ArrayList<JButton> suggestionBtns;
	
	private  JRadioButton titleRadioBtn;
	private  JRadioButton releaseYearRadioBtn;
	private  ButtonGroup group;
	
	private  JPanel upperPanel;
	private  JPanel searchPanel;
	private  JPanel sortPanel;
	private  JPanel correctSpellingPanel;
	private  JPanel resultsPanel;
	
	public GUI() throws IOException, CsvException 
	{
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
	

	private  void initController()  throws IOException, CsvException
	{
		controller=new Controller();
	}
	
	private  void initWindow() 
	{
		Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");      
		f=new JFrame();
		f.setTitle("Movies Search Engine");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setIconImage(icon);
		f.setSize(1024,720);
		f.setResizable(true);
		f.setLayout(new GridLayout(2,1));
	}
	
	private  void initializeUpperPanel() 
	{
		upperPanel=new JPanel();
		upperPanel.setLayout(new GridLayout(3,1));	
	}
	
	private  void initializeSearchPanel() 
	{
		searchPanel= new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		initializeSearchPanelComponents();
		searchPanel.add(searchLabel);
		searchPanel.add(queryTextField);
		searchPanel.add(searchButton);
		searchPanel.add(previousButton);
		searchPanel.add(nextButton);
	}
	
	private  void initializeSearchPanelComponents() 
	{
		searchLabel = new JLabel("Lucene Search Engine");		
		queryTextField= new JTextField();
		queryTextField.setPreferredSize(new Dimension(400,20));
		searchButton = new JButton("Search");
		previousButton = new JButton("Previous Page");
		nextButton = new JButton("Next Page");
		previousButton.setVisible(false);
		nextButton.setVisible(false);
	}
	
	private  void initializeSortPanel() 
	{
		sortPanel=new JPanel();
		sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		initializeSortPanelComponents();
		sortPanel.add(sortByLabel);
		sortPanel.add(titleRadioBtn);
		sortPanel.add(releaseYearRadioBtn);
	}
	
	private  void initializeSortPanelComponents() 
	{
		sortByLabel=new JLabel("Sort By:");
		titleRadioBtn=new JRadioButton("title");
		releaseYearRadioBtn= new JRadioButton("release year");
		titleRadioBtn.setEnabled(false);
		releaseYearRadioBtn.setEnabled(false);
		group= new ButtonGroup();
		group.add(titleRadioBtn);
		group.add(releaseYearRadioBtn);
	}

	private  void initializeCorrectSpellingPanel() 
	{
		correctSpellingPanel= new JPanel();
		correctSpellingPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		initializeCorrectSpellingPanelComponents();
	}
	
	private  void initializeCorrectSpellingPanelComponents() 
	{
		incorrectLabel= new JLabel("Did you mean :");
		incorrectLabel.setVisible(false);
		correctSpellingPanel.add(incorrectLabel);
		suggestionBtns=new ArrayList<JButton>();
		for(int i=0;i<5;i++) 
		{
			JButton suggestionBtn= new JButton("Text");
			suggestionBtn.setVisible(false);
			suggestionBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						disableResultBtns();
						disableCorrectSpellingPanel();
						dumpResultsAndPanel();
						titleRadioBtn.setEnabled(true);
						releaseYearRadioBtn.setEnabled(true);
						group.clearSelection();
						previousButton.setVisible(false);
						nextButton.setVisible(false);
						controller.search(suggestionBtn.getText());
						
						if(controller.noResultsGiven()) 
						{
							controller.setSuggestionBtnsText(suggestionBtns, suggestionBtn.getText());
							enableCorrectSpellingPanel();
							return;
						}
						controller.setFirstSearchResultBtnsText(resultBtns,f);
						
						if(controller.checkForNextRemainingResults()) 
						{
							nextButton.setVisible(true);
						}
											
					} catch (ParseException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 
			});	
			suggestionBtns.add(suggestionBtn);
			correctSpellingPanel.add(suggestionBtn);
		}		
	}
	
	private  void initializeResultsPanel() 
	{
		resultsPanel= new JPanel();
		resultsPanel.setLayout(new GridLayout(10,1,10,10));
		initializeResultBtns();	
	}
	
	private  void initializeResultBtns() 
	{
		resultBtns=new ArrayList<JButton>();
		for(int i=0;i<10;i++) 
		{
			JButton resultButton= new JButton("");
			resultButton.setVisible(false);
			resultBtns.add(resultButton);
			resultsPanel.add(resultButton);
		}
	}
	
	private  void disableResultBtns() 
	{
		for(JButton resultBtn :resultBtns) 
		{
			resultBtn.setVisible(false);
		}
	}
	
	private void dumpResultbuttons() 
	{
		resultBtns=null;
	}
	
	private  void addButtonActionListeners() 
	{
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					disableResultBtns();
					disableCorrectSpellingPanel();
					dumpResultsAndPanel();
					titleRadioBtn.setEnabled(true);
					releaseYearRadioBtn.setEnabled(true);
					group.clearSelection();
					previousButton.setVisible(false);
					nextButton.setVisible(false);
					controller.search(queryTextField.getText());

					if(controller.noResultsGiven()) 
					{
						titleRadioBtn.setEnabled(false);
						releaseYearRadioBtn.setEnabled(false);
						controller.setSuggestionBtnsText(suggestionBtns, queryTextField.getText());
						enableCorrectSpellingPanel();

						return;
					}
					controller.setFirstSearchResultBtnsText(resultBtns,f);
					
					if(controller.checkForNextRemainingResults()) 
					{
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
				dumpResultsAndPanel();
				controller.setNextSearchResultBtnsText(resultBtns,f);
				
				if(!controller.checkForNextRemainingResults()) 
				{
					nextButton.setVisible(false);
				}	
			} 
		});
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableResultBtns();
				dumpResultsAndPanel();
				controller.setPreviousSearchResultBtnsText(resultBtns,f);
				
				if(!controller.checkForPreviousRemainingResults()) 
				{
					previousButton.setVisible(false);
				}
				if(controller.checkForNextRemainingResults()) 
				{
					nextButton.setVisible(true);
				}	
			} 	
		});
		titleRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButton.setVisible(false);
				disableResultBtns();
				dumpResultsAndPanel();
				controller.sortByTitle(resultBtns,f);;

				if(controller.checkForNextRemainingResults()) 
				{
					nextButton.setVisible(true);
				}	
			} 
		});
		releaseYearRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButton.setVisible(false);
				disableResultBtns();
				dumpResultsAndPanel();
				
				controller.sortByYear(resultBtns,f);;

				if(controller.checkForNextRemainingResults()) 
				{
					nextButton.setVisible(true);
				}	
			} 
		});
	}
	
	private void dumpResultsAndPanel() 
	{
		dumpResultbuttons();
		f.remove(resultsPanel);
		initializeResultsPanel();
		f.add(resultsPanel);
	}
	
	private void disableCorrectSpellingPanel() 
	{
		incorrectLabel.setVisible(false);
		for(JButton suggestionBtn :suggestionBtns) 
		{
			suggestionBtn.setVisible(false);
		}
	}
	
	private void enableCorrectSpellingPanel() 
	{
		incorrectLabel.setVisible(true);
		for(JButton suggestionBtn :suggestionBtns) 
		{
			suggestionBtn.setVisible(true);
		}
	}
	
	public JFrame getFrame() 
	{
		return f;
	}

	public static  void main(String[] args) throws IOException, CsvException
	{
			new GUI();
	}
}
