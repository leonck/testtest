package com.quark.wanlihuanyunuser.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;


public class CustomsqDialog extends Dialog {

	public CustomsqDialog(Context context) {
		super(context);
	}

	public CustomsqDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;
		TextView close;
		OnClickListener closelistener;
		OnDismissListener dsmissListener;
		public Builder(Context context) {
			this.context = context;
		}
		
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		
		public Builder setclose(OnClickListener listener) {
			closelistener = listener;
			return this;
		}
		/**
		 * Set the Dialog message from resource
		 * 
		 * @param message
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public CustomsqDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomsqDialog dialog = new CustomsqDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_sqnormal_layout, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			close = (TextView)layout.findViewById(R.id.close);
			close.setOnClickListener(
					new View.OnClickListener() {
						public void onClick(View v) {
							closelistener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
						}
					});
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton)).setOnClickListener(
							new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
//				((LinearLayout) layout.findViewById(R.id.message)).removeAllViews();
//				((LinearLayout) layout.findViewById(R.id.message)).addView(contentView, new LayoutParams(
//								LayoutParams.WRAP_CONTENT,
//								LayoutParams.WRAP_CONTENT));
			}
			
			dialog.setOnDismissListener(dsmissListener);
			
			dialog.setContentView(layout);
			dialog.setCanceledOnTouchOutside(false);
			return dialog;
		}

		public void setOnDismissListener(OnDismissListener onDismissListener) {
			// TODO Auto-generated method stub
			dsmissListener = onDismissListener;
		}

	}
}
