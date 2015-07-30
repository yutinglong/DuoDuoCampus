package com.duoduo.duoduocampus.profile.views;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.utils.DisplayUtil;
import com.duoduo.duoduocampus.utils.MobileUtil;

public class MessageBoxDialog extends Dialog {
	
	private boolean isBlockBackKey = false;

	public MessageBoxDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public MessageBoxDialog(Context context, int theme) {
		super(context, theme);
	}

	public MessageBoxDialog(Context context) {
		super(context);
	}
	
	public void setBlockBackKey(boolean b){
		this.isBlockBackKey = b;
	}

	@Override
	public void onBackPressed() {
		if(isBlockBackKey){
			return;
		}
		super.onBackPressed();
	}

	/** 构建对话框辅助类 */
	public static class Builder {

		private Context context;

		private int visibleCount;
		private boolean cancelable;
		
		/** 对话框标题 */
		private String title;

		/** 对话框标题样式 */
		private int titleStyle;

		/** 对话框内容 */
		private String message;

		/** 对话框内容样式 */
		private int messageStyle;

		/** 按钮1-显示文字 */
		private String doneButtonText;

		/** 按钮1-背景资源 */
		private int doneButtonDrawable;
		
		/** 按钮1-文字样式 */
		private int doneButtonTextStyle;

		/** 按钮2-显示文字 */
		private String cancelButtonText;

		/** 按钮2-背景资源 */
		private int cancelButtonDrawable;
		
		/** 按钮2-文字样式 */
		private int cancelButtonTextStyle;

		/** 按钮3-显示文字 */
		private String otherButtonText;

		/** 按钮3-背景资源 */
		private int otherButtonDrawable;
		
		/** 按钮3-文字样式 */
		private int otherButtonTextStyle;

		/** 对话框背景资源 */
		private int contentViewBackDrawable;

		/**填充布局*/
		private View customView;
		
		private BaseAdapter adapter;
		
		private View contentView;
		
		private DialogInterface.OnClickListener doneButtonClickListener,
				cancelButtonClickListener, otherButtClickListener, singleItemClickListener;

		private EditText editText;
		
		
		private int yOffSet = 0;
		private int xOffSet = 0;
		
		private int gravity = Gravity.CENTER;

		
		private String highLightText;
		
		private int highLightTextColorId;
		
		/**
		 * @param context 
		 * @param visiableButtonCount
		 * 			设置的按钮的个数
		 */
		public Builder(Context context, int visiableButtonCount) {
			this.context = context;
			this.visibleCount = visiableButtonCount;
			this.cancelable = true;
		}

		/**
		 * 设置对话框背景
		 * 
		 * @param backgroudRes
		 *            背景资源ID 注：如果不设置，传0
		 * @return
		 */
		public Builder setMessageBoxDialogBackGround(int backgroudRes) {
			this.contentViewBackDrawable = backgroudRes;
			return this;
		}

		/**
		 * 根据字符串设置对话框消息及文本样式
		 * 
		 * @param message
		 *            要显示的文本
		 * @param messageStyle
		 *            不设置，传0
		 * @return
		 */
		public Builder setMessageAndStyle(String message, int messageStyle) {
			this.message = message;
			this.messageStyle = messageStyle;
			return this;
		}

		/**
		 * 根据字符串资源ID设置对话框消息,并设置文本样式
		 * 
		 * @param message
		 *            资源ID
		 * @param messageStyle
		 *            不设置，传0
		 * @return
		 */
		public Builder setMessageAndStyle(int message, int messageStyle) {
			this.message = (String) context.getText(message);
			this.messageStyle = messageStyle;
			return this;
		}

		/**
		 * 根据字符串资源ID设置对话框标题，并设置文本样式
		 * 
		 * @param title
		 *            资源ID
		 * @param titleStyle
		 *            不设置，传0
		 * @return
		 */
		public Builder setTitleAndStyle(int title, int titleStyle) {
			this.title = (String) context.getText(title);
			this.titleStyle = titleStyle;
			return this;
		}

		/**
		 * 根据字符串设置对话框标题及文本样式
		 * 
		 * @param title
		 *            要显示的文本
		 * @param titleStyle
		 *            不设置，传0
		 * @return
		 */
		public Builder setTitleAndStyle(String title, int titleStyle) {
			this.title = title;
			this.titleStyle = titleStyle;
			return this;
		}

		/**
		 * 设置按钮1的文字内容，及监听器
		 * 
		 * @param doneButtonText
		 *            文本内容
		 * @param doneButtonTextStyle
		 * 			  文本样式
		 * @param doneButtonDrawable
		 *            按钮1背景资源 注：如果不设置，传0
		 * @param listener
		 *            监听器
		 * @return
		 */
		public Builder setDoneButton(int doneButtonText,int doneButtonTextStyle,
				int doneButtonDrawable, DialogInterface.OnClickListener listener) {
			this.doneButtonText = (String) context.getText(doneButtonText);
			return setDoneButton(this.doneButtonText, doneButtonTextStyle, doneButtonDrawable, listener);
		}

		/**
		 * 设置按钮1的文字内容，及监听器
		 * 
		 * @param doneButtonText
		 * 
		 * @param doneButtonTextStyle
		 * 			文本样式
		 * @param doneButtonDrawable
		 *            按钮1背景资源 注：如果不设置，传0
		 * @param listener
		 * @return
		 */
		public Builder setDoneButton(String doneButtonText, int doneButtonTextStyle,
				int doneButtonDrawable, DialogInterface.OnClickListener listener) {
			this.doneButtonText = doneButtonText;
			this.doneButtonTextStyle = doneButtonTextStyle;
			this.doneButtonDrawable = doneButtonDrawable;
			this.doneButtonClickListener = listener;
			return this;
		}
	

		/**
		 * 设置按钮2的文字内容，及监听器
		 * 
		 * @param cancelButtonText
		 * 
		 * @param cancelButtonTextStyle
		 * 			文本样式
		 * @param cancelButtonDrawable
		 *            按钮2背景资源 注：如果不设置，传0
		 * @param listener
		 * @return
		 */
		public Builder setCancelButton(int cancelButtonText,int cancelButtonTextStyle,
				int cancelButtonDrawable,
				DialogInterface.OnClickListener listener) {
			this.cancelButtonText = (String) context.getText(cancelButtonText);
			
			return setCancelButton(this.cancelButtonText, cancelButtonTextStyle, cancelButtonDrawable, listener);
		}

		/**
		 * 设置按钮2的文字内容，及监听器
		 * 
		 * @param cancelButtonText
		 * @param cancelButtonTextStyle
		 * 			文本样式
		 * @param cancelButtonDrawable
		 *            按钮2背景资源 注：如果不设置，传0
		 * @param listener
		 * @return
		 */
		public Builder setCancelButton(String cancelButtonText, int cancelButtonTextStyle,
				int cancelButtonDrawable,
				DialogInterface.OnClickListener listener) {
			this.cancelButtonText = cancelButtonText;
			this.cancelButtonTextStyle = cancelButtonTextStyle;
			this.cancelButtonDrawable = cancelButtonDrawable;
			this.cancelButtonClickListener = listener;
			return this;
		}

		/**
		 * 设置按钮3的文字内容，及监听器
		 * 
		 * @param otherButtonText
		 * @param otherButtonTextStyle
		 * 				按钮文本样式
		 * @param otherButtonDrawable
		 *            按钮3背景资源 注：如果不设置，传0
		 * @param listener
		 * @return
		 */
		public Builder setOtherButton(int otherButtonText,int otherButtonTextStyle,
				int otherButtonDrawable,
				DialogInterface.OnClickListener listener) {
			this.otherButtonText = (String) context.getText(otherButtonText);
			
			return setOtherButton(this.otherButtonText, otherButtonTextStyle, otherButtonDrawable, listener);
		}

		/**
		 * 设置按钮3的文字内容，及监听器
		 * 
		 * @param cancelButtonText
		 * @param otherButtonTextStyle
		 * @param otherButtonDrawable
		 *            按钮3背景资源 注：如果不设置，传0
		 * @param listener
		 * @return
		 */
		public Builder setOtherButton(String otherButtonText, int otherButtonTextStyle,
				int otherButtonDrawble, DialogInterface.OnClickListener listener) {
			this.otherButtonText = otherButtonText;
			this.otherButtonTextStyle = otherButtonTextStyle;
			this.otherButtonDrawable = otherButtonDrawble;
			this.otherButtClickListener = listener;
			return this;
		}
		
		public Builder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		
		public Builder setCustomView(View contentView) {
			this.customView = contentView;
			return this;
		}
		
		public Builder setSingleChoiceItems(BaseAdapter adapter, DialogInterface.OnClickListener listener) {
			this.adapter = adapter;
			this.singleItemClickListener = listener;
			return this;
		}
		
		
		public EditText getEditText() {
			return this.editText;
		}
		
		public Builder withGravity(int gravity, int xOffSet, int yOffSet) {
			this.gravity = gravity;
			this.xOffSet = xOffSet;
			this.yOffSet = yOffSet;
			return this;
		}
		
		public Builder setContentView(View contentView) {
			this.contentView = contentView;
			return this;
		}
		
		public Builder withHighLightText(String highLightText, int colorId) {
			this.highLightText = highLightText;
			this.highLightTextColorId = colorId;
			return this;
		}
		
		/**
		 * 创建自定义的对话框
		 */
		public MessageBoxDialog create() {
			
			final MessageBoxDialog dialog = new MessageBoxDialog(context,
					R.style.MessageBoxDialogStyle);
			Window window = dialog.getWindow();
			WindowManager.LayoutParams param = window.getAttributes();
			param.gravity = gravity;
			param.x = xOffSet;
			param.y = yOffSet;
			param.width = WindowManager.LayoutParams.WRAP_CONTENT;
			param.height = WindowManager.LayoutParams.WRAP_CONTENT;
			window.setAttributes(param);
			if (contentView != null) {
				dialog.setContentView(contentView,new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				return dialog;
			}
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.message_box_dialog, null);
			dialog.setContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			int miniWidth = MobileUtil.getScreenWidthIntPx()*4/5;
			layout.setMinimumWidth(miniWidth);
			
			
			
			LinearLayout customViewContainer =(LinearLayout) layout.findViewById(R.id.custom_layout_container);
			LinearLayout tittleContainer = (LinearLayout) layout.findViewById(R.id.ll_title);
			/* 设置对话框背景 */
			if (contentViewBackDrawable > 0) {
				layout.setBackgroundResource(contentViewBackDrawable);
			}

			/* 设置对话框标题 */
			if (!TextUtils.isEmpty(title)) {
				tittleContainer.setVisibility(View.VISIBLE);
				TextView titleTextView = ((TextView) layout
						.findViewById(R.id.title_text));
				titleTextView.setText(title);
				if (titleStyle > 0) {
					/* 设置标题样式 */
					titleTextView.setTextAppearance(context, titleStyle);
				}
			} else {
				tittleContainer.setVisibility(View.GONE);
			}

			
			LinearLayout messageContainer = (LinearLayout) layout.findViewById(R.id.ll_message);
			if (!TextUtils.isEmpty(message)) {
				HighLightTextView messageTextView = ((HighLightTextView) layout
						.findViewById(R.id.message_text));
				messageContainer.setVisibility(View.VISIBLE);
				messageTextView.setText(message);
				if (messageStyle > 0) {
					messageTextView.setTextAppearance(context, messageStyle);
				}
				
				if (!TextUtils.isEmpty(highLightText) && message.contains(highLightText)) {
					messageTextView.decorateText(message, highLightText, highLightTextColorId);
				}
			} else {
				messageContainer.setVisibility(View.GONE);
			}
			
			if (adapter != null) {
				 ListView listView = new ListView(context);
//				 listView.setDivider();
				 listView.setDivider(new ColorDrawable(context.getResources().getColor(R.color.grey_l12)));
				 listView.setDividerHeight(DisplayUtil.dipToPixels(context, 1));
				 listView.setCacheColorHint(context.getResources().getColor(R.color.transparent));
				// listView.setSelector(null);
				 listView.setSelector(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
				 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				 listView.setAdapter(adapter);
				 listView.setLayoutParams(params);
				 customView = listView;
				 listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (singleItemClickListener != null) {
							singleItemClickListener.onClick(dialog, position);
						}
					}
					 
				});
				customView = listView;
			}
			
			
			
			if (customView != null) {
				customViewContainer.removeAllViews();
				customViewContainer.setVisibility(View.VISIBLE);
				customViewContainer.addView(customView);
			} else {
				customViewContainer.setVisibility(View.GONE);
			}
			
			LinearLayout btnContainer = (LinearLayout)layout.findViewById(R.id.btn_container);
			
			/* 设置按钮1 */
			
			int margin = DisplayUtil.dipToPixels(context, 10);
			boolean marginFlag = false;
			if (visibleCount > 0) {
				
				if (visibleCount == 1) {
					marginFlag = true;
				}
				LinearLayout.LayoutParams buttonParams = null;
				
				if (!TextUtils.isEmpty(doneButtonText)) {
					
					TextView doneButton = new TextView(context);
					buttonParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
					buttonParams.weight = 1.0f;
					//buttonParams.gravity=Gravity.CENTER;
					
					if (marginFlag) {
						buttonParams.rightMargin = margin;
						buttonParams.leftMargin = margin;
						marginFlag = false;
					}
					doneButton.setGravity(Gravity.CENTER);
					if (visibleCount > 1) {
						buttonParams.rightMargin = margin;
					}
					btnContainer.addView(doneButton, buttonParams);
					doneButton.setText(doneButtonText);
					if (doneButtonDrawable > 0) {
						doneButton.setBackgroundResource(doneButtonDrawable);
					} else {
						doneButton.setBackgroundResource(R.drawable.btn_done_bg);
					}
					
					if (doneButtonTextStyle > 0) {
						doneButton.setTextAppearance(context, doneButtonTextStyle);
					}
					
					doneButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (doneButtonClickListener != null)
								doneButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_POSITIVE);
							else {
								dialog.dismiss();
							}
						}
					});
					
				}
				
				if (!TextUtils.isEmpty(cancelButtonText)) {
					
					TextView cancelButton = new TextView(context);
					buttonParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
					buttonParams.weight = 1.0f;
					cancelButton.setGravity(Gravity.CENTER);
					
					if (marginFlag) {
						buttonParams.rightMargin = margin;
						buttonParams.leftMargin = margin;
						marginFlag = false;
					}
					if (btnContainer.getChildCount() > 0) {
						buttonParams.leftMargin = margin;
					} else if (visibleCount > 1) {
						buttonParams.rightMargin = margin;
					}
					btnContainer.addView(cancelButton, buttonParams);
					cancelButton.setText(cancelButtonText);
					if (cancelButtonDrawable > 0) {
						cancelButton.setBackgroundResource(cancelButtonDrawable);
					} else {
						cancelButton.setBackgroundResource(R.drawable.btn_done_bg);
					}
					
					if (cancelButtonTextStyle > 0) {
						cancelButton.setTextAppearance(context, cancelButtonTextStyle);
					}
					cancelButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (cancelButtonClickListener != null)
								cancelButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_NEGATIVE);
							
							else 
								dialog.dismiss();
						}
					});
				}
				
				if (!TextUtils.isEmpty(otherButtonText)) {
					
					TextView otherButton = new TextView(context);
					buttonParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
					buttonParams.weight = 1.0f;
					//buttonParams.gravity = Gravity.CENTER;
					otherButton.setGravity(Gravity.CENTER);
					
					if (marginFlag) {
						buttonParams.leftMargin = margin;
						buttonParams.rightMargin = margin;
						marginFlag = false;
					}
					if (btnContainer.getChildCount() > 0) {
						buttonParams.leftMargin = margin;
					} 
					btnContainer.addView(otherButton, buttonParams);
					otherButton.setText(otherButtonText);
					if (otherButtonDrawable > 0) {
						otherButton.setBackgroundResource(otherButtonDrawable);
					} else {
						otherButton.setBackgroundResource(R.drawable.btn_warn_bg);
					}
					
					if (otherButtonTextStyle > 0) {
						otherButton.setTextAppearance(context, otherButtonTextStyle);
					}
					otherButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (otherButtClickListener != null)
								otherButtClickListener.onClick(dialog,
										DialogInterface.BUTTON_NEUTRAL);
						}
					});
				}
				
				if (btnContainer.getChildCount() != visibleCount) {
					throw new IllegalArgumentException("visible count is not equal to the number of buttons!");
				}
			} else {
				btnContainer.setVisibility(View.GONE);
			}
			
			dialog.setCanceledOnTouchOutside(true);
			dialog.setCancelable(cancelable);
			dialog.setContentView(layout);
			return dialog;
		}

		
		public void show() {
			MessageBoxDialog dialog = create();
			if (!dialog.isShowing()) {
				dialog.show();
			}
		}



        public boolean isShowing(){
            MessageBoxDialog dialog = create();
            return dialog.isShowing();
        }
	}
}
