package com.jrichardsz.app.rgbdroid;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.jrichardsz.app.rgbdroid.core.*;
import com.linet.api.swing.error.*;
import com.linet.api.swing.filechooser.*;
import com.linet.api.swing.lookandfeel.*;
import com.linet.util.file.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

public class MainUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID=1L;
	private JPanel contentPane;
	private JTextField textFieldExportFilePath;
	private JTextField textFieldOutputDirectory;
	private JButton btnBrowseImage;
	private JButton btnExportRgb;
	private JButton buttonBrowseOutputDirectory;
	private JLabel lblSelectATxt;
	private JTextField textFieldRGBFilePath;
	private JButton buttonBrowseRGBFile;
	private JButton btnShowImage;
	private JLabel lblImageWidth;
	private JTextField textFieldWidth;
	private JTextField textFieldHeight;
	private JLabel lblImageHeight;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					WindowUtilities.setNativeLookAndFeel();
					MainUI frame=new MainUI();
					frame.setVisible(true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI(){
		setTitle("RGB Droid");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,242);
		contentPane=new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		contentPane.setLayout(new BorderLayout(0,0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane=new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane,BorderLayout.CENTER);

		JPanel panelExport=new JPanel();
		tabbedPane.addTab("Export",null,panelExport,null);
		panelExport.setLayout(null);

		JLabel lblSelectAnImage=new JLabel("Select an image");
		lblSelectAnImage.setBounds(10,11,407,14);
		panelExport.add(lblSelectAnImage);

		textFieldExportFilePath=new JTextField();
		textFieldExportFilePath.setBounds(10,36,315,20);
		panelExport.add(textFieldExportFilePath);
		textFieldExportFilePath.setColumns(10);

		btnBrowseImage=new JButton("browse");
		btnBrowseImage.addActionListener(this);
		btnBrowseImage.setBounds(335,36,82,23);
		panelExport.add(btnBrowseImage);

		btnExportRgb=new JButton("export");
		btnExportRgb.addActionListener(this);
		btnExportRgb.setBounds(335,148,82,23);
		panelExport.add(btnExportRgb);

		JLabel lblSelectOutputDirectory=new JLabel("Select output directory");
		lblSelectOutputDirectory.setBounds(10,70,407,14);
		panelExport.add(lblSelectOutputDirectory);

		textFieldOutputDirectory=new JTextField();
		textFieldOutputDirectory.setColumns(10);
		textFieldOutputDirectory.setBounds(10,95,315,20);
		panelExport.add(textFieldOutputDirectory);

		buttonBrowseOutputDirectory=new JButton("browse");
		buttonBrowseOutputDirectory.addActionListener(this);
		buttonBrowseOutputDirectory.setBounds(335,95,82,23);
		panelExport.add(buttonBrowseOutputDirectory);

		JPanel panelImport=new JPanel();
		tabbedPane.addTab("Import",null,panelImport,null);
		panelImport.setLayout(null);

		lblSelectATxt=new JLabel("Select a txt file with RGB");
		lblSelectATxt.setBounds(10,11,407,14);
		panelImport.add(lblSelectATxt);

		textFieldRGBFilePath=new JTextField();
		textFieldRGBFilePath.setColumns(10);
		textFieldRGBFilePath.setBounds(10,36,315,20);
		panelImport.add(textFieldRGBFilePath);

		buttonBrowseRGBFile=new JButton("browse");
		buttonBrowseRGBFile.addActionListener(this);
		buttonBrowseRGBFile.setBounds(335,36,82,23);
		panelImport.add(buttonBrowseRGBFile);

		btnShowImage=new JButton("show image");
		btnShowImage.addActionListener(this);
		btnShowImage.setBounds(10,146,407,23);
		panelImport.add(btnShowImage);

		lblImageWidth=new JLabel("Image width");
		lblImageWidth.setBounds(10,70,82,14);
		panelImport.add(lblImageWidth);

		textFieldWidth=new JTextField();
		textFieldWidth.setBounds(102,67,86,20);
		panelImport.add(textFieldWidth);
		textFieldWidth.setColumns(10);

		textFieldHeight=new JTextField();
		textFieldHeight.setColumns(10);
		textFieldHeight.setBounds(102,101,86,20);
		panelImport.add(textFieldHeight);

		lblImageHeight=new JLabel("Image height");
		lblImageHeight.setBounds(10,104,82,14);
		panelImport.add(lblImageHeight);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
		    	JPanel panel=new JPanel();
				JTextField jTextField = new JTextField("https://github.com/jrichardsz/appdesktop-rgbdroid");
				panel.add(jTextField);
		    	
				JOptionPane.showMessageDialog(null,panel,"Developer : JRichardsz :{)",JOptionPane.INFORMATION_MESSAGE);
		    	System.exit(0);
		    }
		});
	}

	@Override
	public void actionPerformed(ActionEvent e){

		if(e.getSource() == btnBrowseImage){

			try{
				String imageFilePath=FileChooserUtil.getFilePathToOpen("Select an jpg image","jpg");
				textFieldExportFilePath.setText(imageFilePath);
			}
			catch(Exception e1){
				ErrorEngine.traceErrorOnUI(e1,false,true);
			}

		}
		else if(e.getSource() == buttonBrowseOutputDirectory){

			try{
				String outputDirectory=FileChooserUtil.getFolderPath("Select a directory destination");
				textFieldOutputDirectory.setText(outputDirectory);
			}
			catch(Exception e1){
				ErrorEngine.traceErrorOnUI(e1,false,true);
			}

		}
		else if(e.getSource() == btnExportRgb){
			try{
				RGBExportEngine rgbExportEngine=new RGBExportEngine();
				rgbExportEngine.execute(textFieldExportFilePath.getText());

				ArrayList<String> pixelStrings=rgbExportEngine.getPixelStrings();
				String filePath=textFieldOutputDirectory.getText() + File.separator + FileUtil.getNameFromPathFile(textFieldExportFilePath.getText()) + "_" + rgbExportEngine.getWidthImage() + "X" + rgbExportEngine.getHeightImage() + ".txt";
				FileUtil.writeFileFromStringCollection(filePath,pixelStrings);
				JOptionPane.showMessageDialog(null,"Success Export RGB","RGB Droid",JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception e1){
				ErrorEngine.traceErrorOnUI(e1,false,true);
			}
		}
		else if(e.getSource() == btnShowImage){

			RGBImportEngine rgbImportEngine=new RGBImportEngine();
			try{
				rgbImportEngine.execute(textFieldRGBFilePath.getText(),Integer.parseInt(textFieldWidth.getText()),Integer.parseInt(textFieldHeight.getText()));
				JPanel panel=new JPanel();
				panel.setLayout(null);
				panel.setPreferredSize(new Dimension(Integer.parseInt(textFieldWidth.getText()),Integer.parseInt(textFieldHeight.getText())+10));
				
				JLabel jLabel = new JLabel(new ImageIcon(rgbImportEngine.getBufferedImageCreated()));
				jLabel.setBounds(0,0,Integer.parseInt(textFieldWidth.getText()),Integer.parseInt(textFieldHeight.getText()));
				panel.add(jLabel);
				JOptionPane.showMessageDialog(null,panel,"Information",JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception e1){
				ErrorEngine.traceErrorOnUI(e1,false,true);
			}
		}else if(e.getSource() == buttonBrowseRGBFile){
			try{
				String rgbFilePath=FileChooserUtil.getFilePathToOpen("Select RGB file","txt");
				textFieldRGBFilePath.setText(rgbFilePath);
			}
			catch(Exception e1){
				ErrorEngine.traceErrorOnUI(e1,false,true);
			}
		}

	}
}
