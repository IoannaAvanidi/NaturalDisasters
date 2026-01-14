package app.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import app.AppController;
import app.gui.jtableview.JTableViewer;
import app.gui.jtableview.SimpleTableModel;
import app.gui.jtableview.LineChartViewerSingleSeries;

public class JFrameLevel00RootFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final AppController appController;
	private final JDesktopPane theDesktop;

	//Constructor Dimiourgei to grafiko perivallon
	public JFrameLevel00RootFrame() {
		super("What a master of disaster");
		appController = new AppController();

		// add desktop pane to frame
		theDesktop = new JDesktopPane();
		this.add(theDesktop);

		// Handle menubar
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);

		//File
		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);


		JMenuItem miLoadTsv = new JMenuItem("Load TSV");
		fileMenu.add(miLoadTsv);
		this.addLoadTsvActionListener(miLoadTsv);

		JMenuItem miExit = new JMenuItem("Exit");
		fileMenu.add(miExit);
		miExit.addActionListener(
				//				(event) -> System.exit(NORMAL) 
				new ActionListener() { 
					public void actionPerformed(ActionEvent e){ 
						int dialogButton =  JOptionPane.showConfirmDialog(null,
								"Are you sure you want to exit?","Warning",JOptionPane.OK_CANCEL_OPTION);

						if(dialogButton == JOptionPane.OK_OPTION){ 
							System.exit(NORMAL);
							System.out.println("Exiting with exit choise " + dialogButton);
						} 	
					}
				}
				);
		
		
		//filters from user 
		JMenu menuFilter = new JMenu("Filters");
		menubar.add(menuFilter);

		JMenuItem miFilterId = new JMenuItem("Filter by Country, Id");
		menuFilter.add(miFilterId);
		this.addFilterByCountryIdActionListener(miFilterId);

		JMenuItem miFilterPrefix = new JMenuItem("Filter by Country, Id, year range");
		menuFilter.add(miFilterPrefix);
		this.addFilterByCountryIdYearRangeActionListener(miFilterPrefix);
		
		
		//Analysis
		JMenu analysisMenu = new JMenu("Analysis");
		menubar.add(analysisMenu);
		
		JMenuItem miDescrStats = new JMenuItem("Descriptive Stats");
		analysisMenu.add(miDescrStats);
		this.addGetDescriptiveStatsActionListener(miDescrStats);

		JMenuItem miRegression = new JMenuItem("Regression");
		analysisMenu.add(miRegression);
		this.addGetRegressionActionListener(miRegression);

		//Report
		JMenu reporterMenu = new JMenu("Report");
		menubar.add(reporterMenu);

		JMenuItem miReportTxt = new JMenuItem("Report txt");
		reporterMenu.add(miReportTxt);
		this.addReportTxtActionListener(miReportTxt);

		JMenuItem miReportMd = new JMenuItem("Report md");
		reporterMenu.add(miReportMd);
		this.addReportMdActionListener(miReportMd);

		JMenuItem miReportHtml = new JMenuItem("Report html");
		reporterMenu.add(miReportHtml);
		this.addReportHtmlActionListener(miReportHtml);

	}// end constructor

	//load files
	private void addLoadTsvActionListener(JMenuItem miLoadCsv) {
		miLoadCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a tsv file");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TSV files", "tsv");
				jfc.addChoosableFileFilter(filter);

				// To add *.* as a possible filter
				jfc.setAcceptAllFileFilterUsed(true);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String pathString = jfc.getSelectedFile().getPath();
					System.out.println("### " + pathString);
					String delimiter = "\t";
					if(pathString.endsWith(".csv"))
						delimiter = ",";
					boolean loaderFlag = false;
					try {
						loaderFlag = appController.load(pathString, delimiter);
					}catch (FileNotFoundException e) {
						System.err.println("File not found for: " + pathString);
						loaderFlag = false;
					} catch (IOException e) {
						System.err.println("Buffered reader readline() failed for: " + pathString);
						loaderFlag = false;
					}
					if(loaderFlag)
						JOptionPane.showMessageDialog(null, "Loading was successful *claping hands*");
					else
						JOptionPane.showMessageDialog(null, "Loading failed for: " + pathString);
				}
			}
		});
	}// end AddLoadCSVListener

	//prwto filtro
	private void addFilterByCountryIdActionListener(JMenuItem miFilterId) {
		
		//JFrameLevel00RootFrame f = this;
		miFilterId.addActionListener(new ActionListener() {
			
			String requestName = "";
			String countryName = "";
			String disasterName = "";

			public void actionPerformed(ActionEvent event) {
				
				//anoigei ena kainourgio parathiro 
				JFrame frame = new JFrame();
				frame.setLayout(new GridLayout(0,1));
				
				//title of the window
	            frame.setTitle("Name your request for country and disasterType");
	            JPanel p = new JPanel(new GridLayout(0,1));
	            p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
	            
	            //first "filter"
	            JLabel label1 = new JLabel("Give your request a name (Country-Type)");
	            p.add(label1);
	            JTextField textfield1 = new JTextField(10);
				p.add(textfield1);
				
				//second "filter"
	            JLabel label2 = new JLabel("Country name?");
	            p.add(label2);
	            JTextField textfield2 = new JTextField(10);
				p.add(textfield2);
				
				//third "filter"
	            JLabel label3 = new JLabel("Indicator name?");
	            p.add(label3);
	            String[] disasters = new String[]{	"Drought", 
	            		"Extreme temperature", "Flood", 
	            		"Landslide", "Storm","Wildfire", 
	            		"TOTAL"};
	            JComboBox<String> disCombo = new JComboBox<String>(disasters);
	            p.add(disCombo);
	            
	            
	            //JTextField textfield3 = new JTextField(10);
				//p.add(textfield3);

	            
	            //otan patietai to koubi OK kane ta parakatw 
				p.add(new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = -1319422410622362398L;

					public void actionPerformed(ActionEvent e) {
						requestName = textfield1.getText();//pare apo to prwto field oti egrapse o xristis
						countryName = textfield2.getText();//pare apo to deytero field oti egrapse o xristis
						//disasterName = textfield3.getText();
						disasterName = (String)disCombo.getItemAt(disCombo.getSelectedIndex()); //pairnei apo ton xristi to indicator name poy exei epilejei
						if((requestName.length() == 0 ) || (countryName.length() == 0 )) {
							System.out.println("Disaster name: "+ disasterName);
							//EAN O xristis den exei simplirwsei kanena pedio 
							JOptionPane.showInternalMessageDialog(null, "All three textboxes must be filled");		
						}else {
							System.out.println("New request: " + requestName  + " " + countryName + " " + disasterName); //display the name 
							try {
								// afto to pernaei sto backend 
								SimpleTableModel tblModel = appController.filterSingleCountryIndicator(requestName, countryName, disasterName);
								showFrameWithTable(tblModel, tblModel.getRequestName());
								showChart(tblModel); //anaparastasi tou chart 
							}catch(IllegalArgumentException exc) {
								JOptionPane.showInternalMessageDialog(null, "My Goodness, how is this even possible? Empty request name");
							}
							//close
							frame.setVisible(false); //you can't see me!
							frame.dispose(); //Destroy the JFrame object
						}
					}
				}));
		        
	            frame.add(p);
	            frame.setSize(500, 500); //original 200 
	            //frame.pack();
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
				

			}//end actionPerformed
		}); //end addAction Listener
	}

	//deytero filtro
	private void addFilterByCountryIdYearRangeActionListener(JMenuItem miFilterId) {
		miFilterId.addActionListener(new ActionListener() {

			String requestName = "";
			String countryName = "";
			String disasterName = "";
			String yearStartString = "";
			String yearEndString = "";
			int startingYear = -1;
			int endingYear = -1;

			public void actionPerformed(ActionEvent event) {
				JFrame frame = new JFrame();
				frame.setLayout(new GridLayout(0,1));
				
				
				//title of the window
	            frame.setTitle("Name your request for country and disasterType");
	            JPanel p = new JPanel(new GridLayout(0,1));
	            p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); 
	            
	            //first filed 
	            JLabel label1 = new JLabel("Give your request a name(Country-Type_Year Range)");
	            p.add(label1);
	            JTextField textfield1 = new JTextField(10);
				p.add(textfield1);
				
				//second field
	            JLabel label2 = new JLabel("Country name?");
	            p.add(label2);
	            JTextField textfield2 = new JTextField(10);
				p.add(textfield2);
				
				//Combo box to select user 
	            JLabel label3 = new JLabel("Indicator name?");
	            p.add(label3);
	            //JTextField textfield3 = new JTextField(10);
				//p.add(textfield3);
	            String[] disasters = new String[]{	"Drought", 
	            		"Extreme temperature", "Flood", 
	            		"Landslide", "Storm","Wildfire", 
	            		"TOTAL"};
	            JComboBox<String> disCombo = new JComboBox<String>(disasters);
	            p.add(disCombo);
	            
	            //Start Year field 
	            JLabel label4 = new JLabel("Year start?");
	            p.add(label4);
	            JTextField textfield4 = new JTextField(10);
				p.add(textfield4);
				
				//End Year Field 
	            JLabel label5 = new JLabel("Year end?");
	            p.add(label5);
	            JTextField textfield5 = new JTextField(10);
				p.add(textfield5);
				
				//Otan patithei to OK botton 
				p.add(new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = -1319422410622362398L;

					public void actionPerformed(ActionEvent e) {
						//pairnei ta pedia opws prin 
						requestName 	= textfield1.getText(); //pare apo to prvto koyti oti egrapse o xristis
						countryName 	= textfield2.getText(); //pare apo to deutero koyti oti egrapse o xristis
//						disasterName 	= textfield3.getText();
						disasterName = (String)disCombo.getItemAt(disCombo.getSelectedIndex()); //Epelekse typo katastrofis
						yearStartString	= textfield4.getText(); //pare apo to 4o koyti oti egrapse o xristis
						yearEndString	= textfield5.getText(); //pare apo to 5o koyti oti egrapse o xristis
						
						if((requestName.length() == 0 ) || (countryName.length() == 0 ) || (disasterName.length() == 0) || (yearStartString.length() == 0) || (yearEndString.length() == 0) ) {
							//Ean o xristis den exei simplirwsei kanena pedio
							JOptionPane.showInternalMessageDialog(null, "All five textboxes must be filled");	
						}else {
							//typwnei 
							System.out.println("New request: " + requestName  + " " + countryName + " " + disasterName + " " + yearStartString + " " + yearEndString);
							startingYear = Integer.valueOf(yearStartString); 
							endingYear = Integer.valueOf(yearEndString);
							
							try {
								SimpleTableModel tblModel = appController.filterSingleCountryIndicatorYearRange(requestName, countryName, disasterName, startingYear, endingYear);			
								showFrameWithTable(tblModel, tblModel.getRequestName());
								showChart(tblModel);
							}catch(IllegalArgumentException exc) {
								JOptionPane.showInternalMessageDialog(null, "My Goodness, how is this even possible? Empty request name"); 
							}
							//close
							frame.setVisible(false); //you can't see me!, pali klaiw
							frame.dispose(); //Destroy the JFrame object
						}
					}
				}));
		        
	            frame.add(p);
	            frame.setSize(500, 500);
	            //frame.pack();
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setVisible(true);
				

;
			}//end actionPerformed		
		}); //end addAction Listener
	}//end addFilterByCountryIdYearRangeActionListener

	//statistika
	private void addGetDescriptiveStatsActionListener(JMenuItem miDescrStats) {
		JFrameLevel00RootFrame fatherFrame = this;
		miDescrStats.addActionListener(new ActionListener() {
			String requestName = "";
			
			public void actionPerformed(ActionEvent event) {
				
				//Title of the window
				JDialog dialog = new JDialog(fatherFrame, "Give the name of the request", true);
				JPanel p = new JPanel();
				
				//Combo box to select the type
	            String[] pastRequests = appController.getRequestNames().toArray(new String[0]);
	            JComboBox<String> reqs = new JComboBox<String>(pastRequests);
	            reqs.setBounds(50, 100,90,20);
	            p.add(reqs);
	            	
	            //OK Button
				p.add(new JButton(new AbstractAction("OK") {
					
					
					private static final long serialVersionUID = 1375436362552716985L;
					
					//ektelese ayto ton kwdika
					public void actionPerformed(ActionEvent e) {
//						requestName = textfield1.getText();
			            requestName = (String)reqs.getItemAt(reqs.getSelectedIndex());
//System.out.println("$$$$$$$$$$$$$$$$$$$$$$ " + requestName);			            
						String result = fatherFrame.appController.getDescriptiveStats(requestName);
						String title = "Descriptive Stats"; 
						showAnalysisResults(title, requestName, result);					
				        dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);
				
				//pare to aitima kai pigene to sto backend
				appController.getDescriptiveStats(requestName);
				
			}//end actionPerformed
		});//end addAction Listener
	}
	
	//regression
	private void addGetRegressionActionListener(JMenuItem miRegression) { 
		JFrameLevel00RootFrame fatherFrame = this;
		miRegression.addActionListener(new ActionListener() {
			String requestName = "";
			
			public void actionPerformed(ActionEvent event) {
				JDialog dialog = new JDialog(fatherFrame, "Give the name of the request", true);
				JPanel p = new JPanel();
	            String[] pastRequests = appController.getRequestNames().toArray(new String[0]);
	            JComboBox<String> reqs = new JComboBox<String>(pastRequests);
	            reqs.setBounds(50, 100,90,20);
	            p.add(reqs);
				p.add(new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = -4510242731905407085L;
					public void actionPerformed(ActionEvent e) {
						requestName = (String)reqs.getItemAt(reqs.getSelectedIndex());
						String result = fatherFrame.appController.getRegression(requestName);
						String title = "Regression Data"; 
						//showAnalysisResults(fatherFrame, title, requestName, result); 
						showAnalysisResults(title, requestName, result);
						dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);
				
				appController.getRegression(requestName);
				
			}//end actionPerformed
		});//end addAction Listener
	}

	//report se txt
	private void addReportTxtActionListener(JMenuItem miReportTxt) {
		JFrameLevel00RootFrame fatherFrame = this;
		miReportTxt.addActionListener(new ActionListener() {
			String requestName = "";
			String fileName = "";
			public void actionPerformed(ActionEvent event) {
				JDialog dialog = new JDialog(fatherFrame, "Give the name of the request", true);
				JPanel p = new JPanel();
				
	            String[] pastRequests = appController.getRequestNames().toArray(new String[0]);
	            JComboBox<String> reqs = new JComboBox<String>(pastRequests);
	            reqs.setBounds(50, 100,90,20);
	            p.add(reqs);
				p.add(new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = 1375436362552716985L;
					public void actionPerformed(ActionEvent e) {
			            requestName = (String)reqs.getItemAt(reqs.getSelectedIndex());
			            dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a target file to save");
			    int destiny = jfc.showSaveDialog(null);
			    if (destiny == JFileChooser.APPROVE_OPTION) {
			    	fileName = jfc.getSelectedFile().getAbsolutePath();
			    }

			    try {
			    	if(!(fileName.toLowerCase().endsWith(".txt")))
			    		fileName = fileName + ".txt";
			    		System.out.println("FILE: " + fileName + " FOR " + requestName);			    	
					int count = appController.createReportText(fileName, requestName);
					System.out.println("COUNT: " + count + "\n\n");					
					JOptionPane.showMessageDialog(null, "Written file " + fileName + " with #rows: " + count);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Saving the report was impossible for file: " + fileName);
				}
			}
		});

	}// end addReportTxtActionListener

	//report se markdown
	private void addReportMdActionListener(JMenuItem miReportMd) {
		JFrameLevel00RootFrame fatherFrame = this;
		miReportMd.addActionListener(new ActionListener() {
			String requestName = "";
			String fileName = "";
			public void actionPerformed(ActionEvent event) {
				JDialog dialog = new JDialog(fatherFrame, "Give the name of the request", true);
				JPanel p = new JPanel();
				
	            String[] pastRequests = appController.getRequestNames().toArray(new String[0]);
	            JComboBox<String> reqs = new JComboBox<String>(pastRequests);
	            reqs.setBounds(50, 100,90,20);
	            p.add(reqs);
				p.add(new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = 1375436362552716985L;
					public void actionPerformed(ActionEvent e) {
			            requestName = (String)reqs.getItemAt(reqs.getSelectedIndex());
			            dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a target file to save");
			    int destiny = jfc.showSaveDialog(null);
			    if (destiny == JFileChooser.APPROVE_OPTION) {
			    	fileName = jfc.getSelectedFile().getAbsolutePath();
			    }

			    try {
			    	if(!(fileName.toLowerCase().endsWith(".md")))
			    		fileName = fileName + ".md";
			    		System.out.println("FILE: " + fileName + " FOR " + requestName);			    	
					int count = appController.createReportMd(fileName, requestName);
					System.out.println("COUNT: " + count + "\n\n");				
					JOptionPane.showMessageDialog(null, "Written file " + fileName + " with #rows: " + count);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Saving the report was impossible for file: " + fileName);
				}
			}
		});

	}// end addReportMdActionListener
	
	//report se html
	private void addReportHtmlActionListener(JMenuItem miReportHtml) {
		JFrameLevel00RootFrame fatherFrame = this;
		miReportHtml.addActionListener(new ActionListener() {
			String requestName = "";
			String fileName = "";
			public void actionPerformed(ActionEvent event) {
				JDialog dialog = new JDialog(fatherFrame, "Give the name of the request", true);
				JPanel p = new JPanel();
				
	            String[] pastRequests = appController.getRequestNames().toArray(new String[0]);
	            JComboBox<String> reqs = new JComboBox<String>(pastRequests);
	            reqs.setBounds(50, 100,90,20);
	            p.add(reqs);
				p.add(new JButton(new AbstractAction("OK") {
					private static final long serialVersionUID = 1375436362552716985L;
					public void actionPerformed(ActionEvent e) {
			            requestName = (String)reqs.getItemAt(reqs.getSelectedIndex());
			            dialog.dispose();
					}
				}));
				dialog.add(p);
				dialog.pack();
				dialog.setVisible(true);
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a target file to save");
			    int destiny = jfc.showSaveDialog(null);
			    if (destiny == JFileChooser.APPROVE_OPTION) {
			    	fileName = jfc.getSelectedFile().getAbsolutePath();
			    }

			    try {
			    	if(!(fileName.toLowerCase().endsWith(".html")))
			    		fileName = fileName + ".html";
			    		System.out.println("FILE: " + fileName + " FOR " + requestName);			    	
					int count = appController.createReportHtml(fileName, requestName);
					System.out.println("COUNT: " + count + "\n\n");					
					JOptionPane.showMessageDialog(null, "Written file " + fileName + " with #rows: " + count);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Saving the report was impossible for file: " + fileName);
				}
			}
		});
	}// end addReportTxtActionListener
	
	
	
	private void showFrameWithTable(SimpleTableModel tblModel, String title) {
		//Grafiki anaparastasi tou aitimatos
		JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
		JTableViewer jTableViewer;

		jTableViewer = new JTableViewer(tblModel);
		frame.add(jTableViewer, BorderLayout.CENTER);
		jTableViewer.createAndShowJTable();
		frame.pack(); // set internal frame to size of contents
		theDesktop.add(frame); // attach internal frame
		frame.setVisible(true); // show internal frame
	}
		
	private void showChart(SimpleTableModel tblModel) {
		String title = tblModel.getRequestName();
		JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
		LineChartViewerSingleSeries reqChart = new LineChartViewerSingleSeries(title, tblModel.getData(), 
				"Year", "CountEvents");
	    XYChart chart = reqChart.getChart();
	    XChartPanel<XYChart> panel = new XChartPanel<>(chart); 
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    
	    frame.add(panel);
	    frame.pack(); 
	    theDesktop.add(frame);
	    frame.setVisible(true);
	}
	
	private void showAnalysisResults(String title, String requestName, String result) {
		JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel(requestName);
        panel.add(label1);
        String localResult = "<html><pre>" + result + "</pre></html>";
        JLabel label2 = new JLabel(localResult);
        panel.add(label2);
        //frame.add(panel);
        frame.add(BorderLayout.CENTER, new JScrollPane(panel));
		//frame.pack(); // set internal frame to size of contents
        frame.setSize(375, 250);
		theDesktop.add(frame); // attach internal frame
		frame.setVisible(true); // show internal frame
	}

	
}// end class
