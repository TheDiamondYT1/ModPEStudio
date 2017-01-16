package tk.thediamondyt.modpeide.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import tk.thediamondyt.modpeide.MainActivity;
import tk.thediamondyt.modpeide.Utils;

public class CodeEditor extends AutoCompleteTextView {

	private Rect rect = new Rect();
	private Paint paint = new Paint();
    
    private SharedPreferences prefs;
    
    private NodeList nodeList = null;
 
    // Unfortunatly i get error when adding regex to xml
    private final Pattern comments = Pattern.compile("/\\*(?:.|[\\n\\r])*?\\*/|//.*");
    private final Pattern symbols = Pattern.compile("[\\+\\-\\*\\^\\&\\?\\!\\=\\|\\<\\>\\:\\/]");
    
    // TODO - Add to the theme xml
    private final Pattern modpe = Pattern.compile(
        "\\b(ModPE|Entity|Level|Player|Block|print|setVelX|setVelY|setVelZ|getPlayerEnt|setTile|rideAnimal|" +
        "clientMessage|getTile|preventDefault)\\b");
    
    private final int COLOUR_COMMENT = Color.GRAY;
    private final int COLOUR_STRING = Color.parseColor("#183691");
    private final int COLOUR_SYMBOL = Color.parseColor("#A81F5E");
    private final int COLOUR_MODPE = Color.parseColor("#7A5FA4");

	public CodeEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.GRAY);
		paint.setTextSize(23);
		setPadding(50, 0, 0, 0);
        // TODO: Remove
        setHint("*Hey there tester! Super fast syntax highlighting. Try spamming numbers.\n\n*Best in portrait mode\n\n" +
                "*This app is still in development and there is some cool features to come!\n\n*Loads syntax highlighting from xml file!\n\n\n" +
                "Credit to Markus Fisch for his app Shader Editor, from which i highly modified the syntax highlighting code.");
        
        prefs = context.getSharedPreferences("tk.thediamondyt.modpeide_preferences", context.MODE_PRIVATE);  
        setupHighlighting();
	}
    
    @Override // TODO: Remove?
    public void onFinishInflate() {
        setHighlighted(getText());
    }

	@Override
	protected void onDraw(Canvas canvas) {
        setHighlighted(getText());
     
        if(prefs.getBoolean("line_number", true)) {
		    int baseline = getBaseline();
		    for (int i = 0; i < getLineCount(); i++) {
			    canvas.drawText("" + (i+1), rect.left, baseline, paint);
			    baseline += getLineHeight();
		    }
        }
		super.onDraw(canvas);
	}
    
    public void setupHighlighting() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
            
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(getResources().getAssets().open("default.xml"));
        } catch(Exception ex) {
         //   Utils.error(MainActivity.get(), ex.getMessage());
        }
        
        doc.getDocumentElement().normalize();
        nodeList = doc.getElementsByTagName("value");
    }
   
    public void setHighlighted(Editable e) {   
        highlight(e);
    }
    
    private Editable highlight(Editable e) {   
        try {
            clearSpans(e);
            if(e.length() == 0) return e;
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item (i);
                
                for(Matcher m = Pattern.compile(element.getAttribute("name")).matcher(e); m.find();) 
                    e.setSpan(new ForegroundColorSpan(Color.parseColor(element.getAttribute("color"))), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            
            // Until i can get regex in xml working
            for(Matcher m = comments.matcher(e); m.find();)
                e.setSpan(new ForegroundColorSpan(COLOUR_COMMENT), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                
            for(Matcher m = symbols.matcher(e); m.find();)
                e.setSpan(new ForegroundColorSpan(COLOUR_SYMBOL), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch(Exception ex) {
               // Utils.error(MainActivity.get(), ex.getMessage());
            }
        return e;
    }

    private void clearSpans(Editable e) {
        {
            ForegroundColorSpan spans[] = e.getSpans(0, e.length(), ForegroundColorSpan.class);
            for(int n = spans.length; n-- > 0;) e.removeSpan(spans[n]);
        }
        {
            BackgroundColorSpan spans[] = e.getSpans(0, e.length(), BackgroundColorSpan.class);
            for(int n = spans.length; n-- > 0;) e.removeSpan(spans[n]);
        }
    }
}
