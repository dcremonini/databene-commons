/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.commons.ui.swing;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.databene.commons.ui.I18NSupport;
import org.databene.commons.ui.swing.delegate.PropertyCheckBox;
import org.databene.commons.ui.swing.delegate.PropertyComboBox;
import org.databene.commons.ui.swing.delegate.PropertyPasswordField;
import org.databene.commons.ui.swing.delegate.PropertyTextArea;
import org.databene.commons.ui.swing.delegate.PropertyTextField;

/**
 * {@link AlignedPane} implementation that supports property-based input elements.<br/><br/>
 * Created: 22.08.2010 07:09:34
 * @since 0.5.13
 * @author Volker Bergmann
 * @param <E> The type of the bean displayed
 */
public class AlignedPropertyPane<E> extends AlignedPane {
	
	private static final long serialVersionUID = 4787396114405246065L;

	private static final int WIDE = 30;
	
	protected final E bean; 
	protected final I18NSupport i18n;
	
	public AlignedPropertyPane(int orientation, int columns, E bean, I18NSupport i18n) {
		super(orientation, columns);
	    this.bean = bean;
	    this.i18n = i18n;
    }

	public E getBean() {
    	return bean;
    }

	public void createI18NLabelRow(String... keys) {
		for (String key : keys)
			this.addElement(new JLabel(i18n.getString(key)));
		endRow();
	}
	
	public JTextField createTextFieldRow(String propertyName) {
		JTextField textfield = new PropertyTextField(bean, propertyName, WIDE);
		String label = i18n.getString(propertyName);
		this.addRow(label, textfield);
		return textfield;
	}

    public JTextField createTextField(String propertyName) {
		return createTextField(propertyName, 1, true);
	}

	public JTextField createTextField(String propertyName, boolean useLabel) {
		return createTextField(propertyName, 1, useLabel);
	}
	
	public JTextField createTextField(String propertyName, int gridwidth, boolean useLabel) {
		JTextField textfield = new PropertyTextField(bean, propertyName, WIDE / 2);
		if (useLabel) {
			String label = i18n.getString(propertyName);
			this.addElement(label, textfield, gridwidth);
		} else
			this.addElement(textfield, gridwidth);
		return textfield;
	}
	
	public JTextArea createTextArea(String propertyName) {
		JTextArea textArea = new PropertyTextArea(bean, propertyName);
		String label = i18n.getString(propertyName);
		this.addLabelRow(label);
		this.addTallRow(new JScrollPane(textArea));
		return textArea;
	}
	
    public JCheckBox createCheckBox(String propertyName) {
		PropertyCheckBox checkBox = new PropertyCheckBox(bean, propertyName, i18n.getString(propertyName));
		this.addElement(checkBox);
		return checkBox;
	}

	public JComboBox createComboBoxRow(String propertyName, Object... options) {
		JComboBox comboBox = createComboBox(propertyName, true, true, options);
		this.endRow();
		return comboBox;
	}

	public JComboBox createComboBox(String propertyName, boolean useLabel, boolean contentIi18n, Object... options) {
	    JComboBox comboBox = new PropertyComboBox(bean, propertyName, (contentIi18n ? i18n : null), propertyName + ".", options);
	    if (useLabel) {
			String label = this.i18n.getString(propertyName);
			this.addElement(label, comboBox);
	    } else {
			this.addElement(comboBox);
	    }
	    return comboBox;
    }

	public JTextField createPasswordField(String propertyName) {
		PropertyPasswordField pwfield = new PropertyPasswordField(bean, propertyName, WIDE / 2);
		String label = i18n.getString(propertyName);
		this.addElement(label, pwfield);
		return pwfield;
	}
	
	public JButton createButton(String label, ActionListener listener) {
		JButton button = new JButton(i18n.getString(label));
		button.addActionListener(listener);
		return button;
	}
	
}
