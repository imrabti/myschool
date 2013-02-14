package com.gsr.myschool.common.client.widget;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.consumersunion.stories.common.client.resource.CommonResources;
import org.consumersunion.stories.common.client.resource.RichTextToolbarImages;
import org.consumersunion.stories.common.client.resource.RichTextToolbarStrings;

public class RichTextToolbar extends Composite {
	public interface Binder extends UiBinder<Widget, RichTextToolbar> {
	}

	private class EventHandler implements ClickHandler, KeyUpHandler {
		public void onClick(ClickEvent event) {
			Widget sender = (Widget) event.getSource();

			if (sender == bold) {
				formatter.toggleBold();
			} else if (sender == italic) {
				formatter.toggleItalic();
			} else if (sender == underline) {
				formatter.toggleUnderline();
			} else if (sender == createLink) {
				String url = Window.prompt("Enter a link URL:", "http://");
				if (url != null) {
					formatter.createLink(url);
				}
			} else if (sender == removeLink) {
				formatter.removeLink();
			} else if (sender == ol) {
				formatter.insertOrderedList();
			} else if (sender == ul) {
				formatter.insertUnorderedList();
			} else if (sender == removeFormat) {
				formatter.removeFormat();
			} else if (sender == richText) {
				updateStatus();
			}
		}

		public void onKeyUp(KeyUpEvent event) {
			Widget sender = (Widget) event.getSource();
			if (sender == richText) {
				updateStatus();
			}
		}
	}

    @UiField
    Button bold;
    @UiField
    Button italic;
    @UiField
    Button underline;

    private EventHandler handler;
    private RichTextArea richText;
    private RichTextArea.Formatter formatter;

	private PushButton createLink;
	private PushButton removeLink;
	private PushButton removeFormat;

	@Inject
	public RichTextToolbar(final Binder binder) {
		handler = new EventHandler();

		initWidget(binder.createAndBindUi(this));

        bold.addClickHandler(handler);
        italic.addClickHandler(handler);

	}

	public void initialize(RichTextArea richText) {
		this.richText = richText;
		this.formatter = richText.getFormatter();

		if (formatter != null) {
			topPanel.add(createSeparator());
			topPanel.add(createSeparator());
			topPanel.add(createLink = createPushButton(richTextToolbarImages.createLink(),
					richTextToolbarStrings.createLink()));
			topPanel.add(removeLink = createPushButton(richTextToolbarImages.removeLink(),
					richTextToolbarStrings.removeLink()));
			topPanel.add(createSeparator());
			topPanel.add(removeFormat = createPushButton(richTextToolbarImages.removeFormat(),
					richTextToolbarStrings.removeFormat()));
		}

		if (formatter != null) {
			richText.addKeyUpHandler(handler);
			richText.addClickHandler(handler);
		}
	}

	private PushButton createPushButton(ImageResource img, String tip) {
		PushButton pb = new PushButton(new Image(img));
		pb.addClickHandler(handler);
		pb.setTitle(tip);
		pb.getElement().setAttribute("style", "display:inline-block");
		return pb;
	}

	private ToggleButton createToggleButton(ImageResource img, String tip) {
		ToggleButton tb = new ToggleButton(new Image(img));
		tb.addClickHandler(handler);
		tb.setTitle(tip);

		tb.getElement().setAttribute("style", "display:inline-block");
		return tb;
	}

	private Label createSeparator() {
		Label separator = new Label();
		separator.setStyleName(commonResources.richTextToolbarStyle().separator());
		return separator;
	}

	private void updateStatus() {
		if (formatter != null) {
			bold.setDown(formatter.isBold());
			italic.setDown(formatter.isItalic());
			underline.setDown(formatter.isUnderlined());
		}
	}
}
