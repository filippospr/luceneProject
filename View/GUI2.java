package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ItemListener;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import com.opencsv.exceptions.CsvException;



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI2 {
	/////////////////////////////////////FIELDS/////////////////////////////////
	public JFrame frame;
	private static String query;
	private static ArrayList<JLabel> listOfLabels;
//	private static ArrayList<MListener> listOfListeners;
//	private static HistoryManager history;
	private JButton nextPageButton;
	private JTextField queryTextField;
	private CardLayout c;	
	private static JLabel resultLabel1;
	private static JLabel resultLabel2;
	private static JLabel resultLabel3;
	private static JLabel resultLabel4;
	private static JLabel resultLabel5;
	private static JLabel resultLabel6;
	private static JLabel resultLabel7;
	private static JLabel resultLabel8;
	private static JLabel resultLabel9;
	private static JLabel resultLabel10;

	private JLabel pageNumberLabel;
	private JLabel searchEngineLabel;
	private JButton searchButton;
	private JButton clearButton;
	private JRadioButton default_b;
	private JRadioButton date;
	private JRadioButton replies;
	private JRadioButton retweets;
	private JRadioButton likes;
	private JLabel simpleSortByLabel;
	private JRadioButton relevance;
	private JRadioButton indexOrder;
	private JLabel simpleOrderByLabel;
	private JButton btnPreviousPage;
	private int currentPage;
	private int pages;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	
	public static void main(String[] args) throws IOException, CsvException, ParseException
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI2 mainWindow = new GUI2();
					mainWindow.initialize();
//					controller.setVisibleResults(false, 1);
					mainWindow.frame.setVisible(true);		
//					controller.makeIndex();
//					history.loadHistory();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public GUI2() throws IOException
	{
//		history = new HistoryManager();
//		controller = new Controller();
	}
	

	public void initialize()
	{
		initializeFrame();
		JPanel panel = new JPanel();
		initializeLabels();
		initializeListeners();
		frame.getContentPane().add(panel, "1");
		initializeOtherButtonsAndLabels();
		intializeComponents(panel);
	
		
		///////////////////////////////////////ACTION LISTENERS//////////////////////////////////////////
//		nextPageButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				btnPreviousPage.setVisible(true);       //gia na pathsa to nextpage simainei oti briskomai toulaxiston sth 2h selida ara thelw na mporw na paw pisw
//				if (currentPage < pages) {
//					currentPage +=1;
//					controller.setVisibleResults(false, currentPage);
//					pageNumberLabel.setText("Page "+String.valueOf(currentPage));
//					controller.getResults(currentPage-1);
//				}
//				if(currentPage == pages)
//				{
//					nextPageButton.setVisible(false);
//				}
//			}
//		});
//		
//		
//		btnPreviousPage.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				nextPageButton.setVisible(true);
//				if (currentPage > 1) {
//					currentPage -=1;
//					controller.setVisibleResults(false, currentPage);
//					pageNumberLabel.setText("Page "+String.valueOf(currentPage));
//					controller.getResults(currentPage-1);
//				}
//				if(currentPage == 1)
//				{
//					btnPreviousPage.setVisible(false);	
//				}
//			}
//		});
//		
//		queryTextField.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode()==KeyEvent.VK_ENTER){
//					enterOrSearch();
//		        }
//			}
//		});
//		queryTextField.setColumns(10);
//		
//		searchButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				enterOrSearch();
//			}
//		});
//
//		default_b.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setFilter("default");;
//				}
//			}
//		});
//		default_b.setSelected(true);
//		
//		clearButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				queryTextField.setText("");
//			}
//		});
//		
//		date.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setFilter("created_at");
//				}
//			}
//		});
//		
//		replies.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setFilter("replies_count");
//				}	
//			}
//		});
//		
//		retweets.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setFilter("retweets_count");
//				}	
//			}
//		});
//		
//		relevance.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setSort("Relevance");
//				}
//			}
//		});
//		relevance.setSelected(true);
//		
//		likes.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setFilter("likes_count");
//				}	
//			}
//		});
//		
//		indexOrder.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange()==1)
//				{
//					controller.setSort("IndexOrder");
//				}
//			}
//		});
//
}
	
	private void enterOrSearch() {
//		nextPageButton.setVisible(false);
//		currentPage = 1;
//		pageNumberLabel.setText("Page "+String.valueOf(currentPage));
//		query=queryTextField.getText();
//		history.create(query);
//		if (query.equals("")) 
//		{
//			JOptionPane.showMessageDialog(frame, "You should type something in the text field \nTry again");
//			
//		}else 
//		{
//			try {
//				controller.setVisibleResults(false, currentPage);
//				pages = controller.searchIndex(query);
//				if (pages > 1) {
//					nextPageButton.setVisible(true);
//				}
//				if (pages>=1) {
//					controller.getResults(0);
//				}
//				if(pages == -1){				
//					String [] suggestions = controller.getSuggestions();
//					if(suggestions.length != 0)
//					{
//						controller.setSuggestions(suggestions);	
//						
//					}else
//					{
//						JOptionPane.showMessageDialog(frame, "Cannot find results for this search \nTry again");
//					}
//					
//				}
//			} catch (IOException | CsvException | ParseException e1) {
//				e1.printStackTrace();
//			}
//		}
	}
	
	public void initializeFrame() {
		frame = new JFrame();
		frame.setSize(1366, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = new CardLayout();
		frame.getContentPane().setLayout(c);
	}
	
	public void initializeLabels() {
		resultLabel1 = new JLabel();		
		resultLabel2 = new JLabel();
		resultLabel3 = new JLabel();
		resultLabel4 = new JLabel();
		resultLabel5 = new JLabel();
		resultLabel6 = new JLabel();
		resultLabel7 = new JLabel();
		resultLabel8 = new JLabel();
		resultLabel9 = new JLabel();
		resultLabel10 = new JLabel();
		makeListOfLabels();
//		controller.setLabels(listOfLabels);
	}
	
	private void initializeListeners() {
//		result1 = new MListener();
//		resultLabel1.addMouseListener(result1);
//		result2 = new MListener();
//		resultLabel2.addMouseListener(result2);
//		result3 = new MListener();
//		resultLabel3.addMouseListener(result3);
//		result4 = new MListener();
//		resultLabel4.addMouseListener(result4);
//		result5 = new MListener();
//		resultLabel5.addMouseListener(result5);
//		result6 = new MListener();
//		resultLabel6.addMouseListener(result6);
//		result7 = new MListener();
//		resultLabel7.addMouseListener(result7);
//		result8 = new MListener();
//		resultLabel8.addMouseListener(result8);
//		result9 = new MListener();
//		resultLabel9.addMouseListener(result9);
//		result10 = new MListener();
//		resultLabel10.addMouseListener(result10);
//		makeListOfListeners();
//		controller.setListeners(listOfListeners);
	
	}
	public void initializeOtherButtonsAndLabels() {
		nextPageButton = new JButton("Next Page");
		nextPageButton.setVisible(false);
		pageNumberLabel = new JLabel("Page 1");
		searchEngineLabel = new JLabel("GoogleFromLidl");
		queryTextField = new JTextField();
		searchButton = new JButton("Search");
		clearButton = new JButton("Clear");
		default_b = new JRadioButton("Default");
		buttonGroup.add(default_b);
		date = new JRadioButton("Date");
		buttonGroup.add(date);
		replies = new JRadioButton("Replies");
		buttonGroup.add(replies);
		retweets = new JRadioButton("Retweets");
		buttonGroup.add(retweets);
		likes = new JRadioButton("Likes");
		buttonGroup.add(likes);
		simpleSortByLabel = new JLabel("Sort descending by:");
		relevance = new JRadioButton("Relevance (default)");
		buttonGroup_1.add(relevance);
		indexOrder = new JRadioButton("Index Order");
		buttonGroup_1.add(indexOrder);
		simpleOrderByLabel = new JLabel("Order by:");
		btnPreviousPage = new JButton("Previous Page"); 
		btnPreviousPage.setVisible(false);
	}
	
	private void intializeComponents(JPanel firstPanel) {
		GroupLayout gl_firstPanel = new GroupLayout(firstPanel);
		gl_firstPanel.setHorizontalGroup(
			gl_firstPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_firstPanel.createSequentialGroup()
					.addGroup(gl_firstPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_firstPanel.createSequentialGroup()
							.addGap(436)
							.addComponent(pageNumberLabel))
						.addGroup(gl_firstPanel.createSequentialGroup()
							.addGap(25)
							.addGroup(gl_firstPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_firstPanel.createSequentialGroup()
									.addGroup(gl_firstPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(resultLabel1, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel2, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel3, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel4, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel5, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel6, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel7, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel8, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel9, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel10, GroupLayout.PREFERRED_SIZE, 881, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE))
								.addGroup(gl_firstPanel.createSequentialGroup()
									.addGroup(gl_firstPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(simpleOrderByLabel)
										.addComponent(searchEngineLabel)
										.addComponent(simpleSortByLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_firstPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_firstPanel.createSequentialGroup()
											.addComponent(default_b)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(date)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(replies)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(retweets)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(likes))
										.addGroup(gl_firstPanel.createSequentialGroup()
											.addComponent(queryTextField, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
											.addGap(18)
											.addComponent(searchButton)
											.addGap(18)
											.addComponent(clearButton)
											.addGap(34))
										.addGroup(gl_firstPanel.createSequentialGroup()
											.addComponent(relevance)
											.addGap(18)
											.addComponent(indexOrder)))))))
					.addContainerGap())
				.addGroup(gl_firstPanel.createSequentialGroup()
					.addGap(49)
					.addComponent(btnPreviousPage)
					.addPreferredGap(ComponentPlacement.RELATED, 707, Short.MAX_VALUE)
					.addComponent(nextPageButton)
					.addGap(21))
		);
		gl_firstPanel.setVerticalGroup(
			gl_firstPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_firstPanel.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_firstPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchEngineLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(queryTextField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(clearButton)
						.addComponent(searchButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_firstPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(default_b)
						.addComponent(date)
						.addComponent(replies)
						.addComponent(retweets)
						.addComponent(likes)
						.addComponent(simpleSortByLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_firstPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(relevance)
						.addComponent(indexOrder)
						.addComponent(simpleOrderByLabel))
					.addGap(13)
					.addComponent(resultLabel1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel5, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel6, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel7, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel8, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel9, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resultLabel10, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_firstPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nextPageButton)
						.addComponent(btnPreviousPage))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(pageNumberLabel)
					.addContainerGap())
		);
		firstPanel.setLayout(gl_firstPanel);
	}
	
	private void makeListOfLabels()
	{
		listOfLabels = new ArrayList<JLabel>();
		List<JLabel> labels = Arrays.asList(resultLabel1, resultLabel2, resultLabel3, resultLabel4, resultLabel5, resultLabel6, resultLabel7, resultLabel8, resultLabel9, resultLabel10);
		listOfLabels.addAll(labels);
	}
	
	private void makeListOfListeners() {
//		listOfListeners= new ArrayList<MListener>();
//		List<MListener> mouseListeners = Arrays.asList(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10);
//		listOfListeners.addAll(mouseListeners);
	}
	
	public static void setVisibleResults(boolean flag, int numOfResults)
	{
		for (int i = 0; i< numOfResults; i++) {
			listOfLabels.get(i).setVisible(flag);
		}
	}
	

}