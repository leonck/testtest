package com.quark.wanlihuanyunuser.ui.widget;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;


public class ChedaiEditDialog extends Dialog {

	public ChedaiEditDialog(Context context) {
		super(context);
	}

	public ChedaiEditDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message,message2;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private EditText editView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		Handler handler;
		public Builder(Context context,Handler handler) {
			this.context = context;
			this.handler = handler;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public Builder setMessage2(String message2) {
			this.message2 = message2;
			return this;
		}
		/**
		 * Set the Dialog message from resource
		 *
		 * @param
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}
		/**
		 * Set the Dialog message2 from resource
		 *
		 * @param
		 * @return
		 */
		public Builder setMessage2(int message2) {
			this.message2 = (String) context.getText(message2);
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
		 * 返回填写的内容
		 * @return
		 */
		public String getContent(){
			if(editView.getText().toString().isEmpty()){
				return "";
			}

			return editView.getText().toString();
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

		public void submint(String password){
			Message msg = new Message();
			msg.obj = password;
			msg.what = 201;
			handler.sendMessage(msg);
		}

		int lastPostion=-1;
		String st="";
		public void setvisiable(int start,String s){
			if((lastPostion==start||lastPostion>start)&&st.length()>s.length()){//删除
				dian[start].setVisibility(View.INVISIBLE);
			}else{									  //增加
				dian[start].setVisibility(View.VISIBLE);
				for(int i = start+1;i<6;i++){
					dian[i].setVisibility(View.INVISIBLE);
				}
			}
			if((start==0&&lastPostion==1)||(start==0&&lastPostion==0)){   //fuck wen ti
				lastPostion = -1;
			}else{
				lastPostion = start;
			}
			st = s;
		}

		TextView one;
		TextView two;
		TextView three ;
		TextView four;
		TextView five;
		TextView six;

		final TextView[] dian = new TextView[6];
		public ChedaiEditDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final ChedaiEditDialog dialog = new ChedaiEditDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_pay_layout, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
			one = (TextView)layout.findViewById(R.id.one);
			two = (TextView)layout.findViewById(R.id.two);
			three = (TextView)layout.findViewById(R.id.three);
			four = (TextView)layout.findViewById(R.id.four);
			five = (TextView)layout.findViewById(R.id.five);
			six = (TextView)layout.findViewById(R.id.six);
			dian[0]=one;dian[1]=two;dian[2]=three;dian[3]=four;dian[4]=five;dian[5]=six;

			editView =(EditText)layout.findViewById(R.id.password);
			editView.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
//					System.out.println("onTextChanged "+" s="+s.toString()+"  start="+start+"  before = "+before+"  count"+count);
					setvisiable(start,s.toString());
					if(start==5){
						String ed = editView.getText().toString();
						submint(ed);
						dialog.dismiss();
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub
					System.out.println("=======beforeTextChanged===== "+" s="+s.toString()+"  start="+start+"  after = "+after+"  count"+count);
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});

			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

//			if (positiveButtonText != null) {
//				((Button) layout.findViewById(R.id.positiveButton))
//						.setText(positiveButtonText);
//				if (positiveButtonClickListener != null) {
//					((Button) layout.findViewById(R.id.positiveButton))
//							.setOnClickListener(new View.OnClickListener() {
//								public void onClick(View v) {
//									positiveButtonClickListener.onClick(dialog,
//											DialogInterface.BUTTON_POSITIVE);
//								}
//							});
//				}
//			} else {
				// if no confirm button just set the visibility to GONE
//				layout.findViewById(R.id.positiveButton).setVisibility(
//						View.GONE);
//			}
			// set the cancel button
			if (negativeButtonText != null) {
//				((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((ImageView) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
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
//				((LinearLayout) layout.findViewById(R.id.message)).addView(contentView, new LayoutParams(LayoutParams.WRAP_CONTENT,
//								LayoutParams.WRAP_CONTENT));
			}

			if (message2 != null) {
				((TextView) layout.findViewById(R.id.message2)).setText(message2);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
//				((LinearLayout) layout.findViewById(R.id.message2))
//				.removeAllViews();
//				((LinearLayout) layout.findViewById(R.id.message2)).addView(
//						contentView, new LayoutParams(
//								LayoutParams.WRAP_CONTENT,
//								LayoutParams.WRAP_CONTENT));
			}
			dialog.setContentView(layout);
			dialog.setCanceledOnTouchOutside(false);
			return dialog;
		}

	}
}
