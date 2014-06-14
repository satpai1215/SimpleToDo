package com.example.simpletodolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        
        //adapter creates a connection between the data and the view
        //allows you to display contents of ArrayList within ListView
        itemsAdapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        
        
        setupListViewListener();
        
    }
    
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> parent,
    				View view, int position, long rowId) {
    			items.remove(position);
    			itemsAdapter.notifyDataSetChanged();
    			saveItems();
    			return true;
    		}
    	});
    }
    
    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}	
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	String text = etNewItem.getText().toString();
    	itemsAdapter.add(text);
    	
    	etNewItem.setText("");
    	saveItems();
    }
    
    


}
