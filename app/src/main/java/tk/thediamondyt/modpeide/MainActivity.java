package tk.thediamondyt.modpeide;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;

import tk.thediamondyt.modpeide.widget.CodeEditor;
import tk.thediamondyt.modpeide.activities.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    
    private static MainActivity context;
    private CodeEditor editor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        context = this;
        editor = (CodeEditor) findViewById(R.id.editorView);
        
      // TODO - Autocomplete
      // String[] k = new String[]{"ModPE.getLol()", "setLol", "dkekkekd"};
      // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, k);
      // editor.setAdapter(adapter); 
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
            case R.id.action_replace:
                // Crashes :( - (Array out of bounds exception)
                new AlertDialog.Builder(this).setTitle("Replace (Crashes)").setView(R.layout.replace_dialog)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Replace", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int something) {
                            String replace = ((EditText) findViewById(R.id.replaceText)).getText().toString();
                            String with = ((EditText) findViewById(R.id.replaceWith)).getText().toString();
                            String text = editor.getText().toString();

                            if(text.contains(replace)) {
                                editor.setText(text.replace(replace, with));
                            } else {
                                // TODO
                            }
                        }
                    }).create().show();           
                break;
			case R.id.action_settings:
				startActivity(new Intent(MainActivity.this, SettingsActivity.class));
				break;
			default:
			    return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}
    
    public static MainActivity get() {
        return context;
    }
}
