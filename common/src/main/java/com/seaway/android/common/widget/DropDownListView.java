/**********************************************************************
 * FILE                ：DropDownListView.java
 * PACKAGE			   ：com.seaway.android.common.widget
 * AUTHOR              ：xubt
 * DATE				   ：2014-4-3 下午4:24:17
 * FUNCTION            ：
 *
 * 杭州思伟版权所有
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE    | NAME           | REASON            | CHANGE REQ.
 *----------------------------------------------------------------------
 *         |          | xubt        | Created           |
 *
 * DESCRIPTION:
 *
 ***********************************************************************/
package com.seaway.android.common.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.seaway.android.common.BuildConfig;
import com.seaway.android.common.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;


/**
 * 项目名称:seaway-android-common
 * 类名称:DropDownListView
 * 类描述：下拉刷新，上拉加载更多
 * 创建人：xubt
 * 创建时间:2014-4-3 下午4:24:17
 * -------------------------------修订历史--------------------------
 * 修改人：xubt
 * 修改时间:2014-4-3 下午4:24:17
 * 修改备注：
 * @version：
 */
public class DropDownListView extends ListView implements OnScrollListener {
	private final static String TAG = "DropDownListView";

	private boolean isHeaderRefresh = true;// 是否下拉刷新
	private boolean isFooterRefresh = true;// 是否底部加载（更多）
	private boolean isAutoFooterRefresh = false;// 自动加载
	
	private final static int DONE = 0;//默认，正常状态
	private final static int PULL_To_REFRESH = 1;//下拉刷新
	private final static int RELEASE_To_REFRESH = 2;//松开刷新
	private final static int REFRESHING = 3;//正在刷新
	
	
	private Drawable headerProgressBarIndeterminateDrawable;
	private Drawable footerProgressBarIndeterminateDrawable;
	private Drawable drowDownDrawable;

	private int headerTextColor;
	private int headerSecondTextColor;
	private int headerTextSize;
	private int headerSecondTextSize;
	private int footerTextColor;
	private int footerTextSize;

	private String headerDefaultStr;
	private String headerPullStr;
	private String headerReleaseStr;
	private String headerLoadingStr;
	
	private String footerMoreStr;
	private String footerLoadingStr;
	private String footerNoMoreStr;

	private Context context;

	/** header layout view **/
	private View headerLayout;
	private ImageView headerImage;
	private ProgressBar headerProgressBar;
	private TextView headerText;
	private TextView headerSecondText;
	 /** footer layout view **/
    private View footerLayout;
    private RelativeLayout rootView ;
    private ProgressBar footerProgressBar;
    private TextView footerText;

    private OnHeaderRefreshListener onHeaderRefreshListener;//顶部刷新事件
    private OnFooterRefreshListener onFooterRefreshListener;//底部刷新事件
    
 // 实际的padding的距离与界面上偏移距离的比例
 	private final static int RATE = 3;
 	private int headContentHeight;//header view 高度
    
    /** image flip animation **/
    private RotateAnimation flipAnimation;
    /** image reverse flip animation **/
    private RotateAnimation reverseFlipAnimation;
    
    /**底部是否还有更多*/
    private boolean hasMore  = true;
    /** whether is on bottom loading **/
    private boolean isOnFooterLoading = false;
    /** whether show footer loading progress bar when loading **/
    private boolean isShowFooterProgressBar = true;
    /** whether show footer when no more data **/
    private boolean isShowFooterWhenNoMore  = true;
    
	/**用于保证startY的值在一个完整的touch事件中只被记录一次*/
	private boolean isRecored;
	private boolean isBack;//是否是由松开刷新状态到下拉刷新状态
    private int currentState;//状态
    private int startY;
	private int firstItemIndex;
	
	

	/**
	 * @param context
	 */
	public DropDownListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public DropDownListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		getAttrs(context, attrs);
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DropDownListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttrs(context, attrs);
		init(context);
	}
	
	 /**
     * get attrs
     * 
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
    	Resources res = getResources();
    	int defauldHeaderTextSize = res.getDimensionPixelSize(R.dimen.default_header_text_size);
    	int defauldHeaderSecondTextSize = res.getDimensionPixelSize(R.dimen.default_header_second_text_size);
    	int defauldFooterTextSize = res.getDimensionPixelSize(R.dimen.default_header_text_size);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.drop_down_list_attr);
        isHeaderRefresh = ta.getBoolean(R.styleable.drop_down_list_attr_isHeaderRefresh, false);
        isFooterRefresh = ta.getBoolean(R.styleable.drop_down_list_attr_isFooterRefresh, false);
        isAutoFooterRefresh = ta.getBoolean(R.styleable.drop_down_list_attr_isAutoFooterRefresh, false);
        drowDownDrawable = ta.getDrawable(R.styleable.drop_down_list_attr_headArrowDrawable);
        headerProgressBarIndeterminateDrawable =ta.getDrawable(R.styleable.drop_down_list_attr_headerIndeterminateDrawable);
        footerProgressBarIndeterminateDrawable =ta.getDrawable(R.styleable.drop_down_list_attr_footerIndeterminateDrawable);
        headerTextColor =ta.getColor(R.styleable.drop_down_list_attr_headerTextColor, Color.BLACK);
        headerSecondTextColor =ta.getColor(R.styleable.drop_down_list_attr_headerSecondTextColor, Color.BLACK);
        footerTextColor =ta.getColor(R.styleable.drop_down_list_attr_footerTextColor, Color.BLACK);
        headerTextSize =ta.getDimensionPixelSize(R.styleable.drop_down_list_attr_headerTextSize, defauldHeaderTextSize);
        headerSecondTextSize = ta.getDimensionPixelSize(R.styleable.drop_down_list_attr_headerSecondTextSize, defauldHeaderSecondTextSize);
        footerTextSize = ta.getDimensionPixelSize(R.styleable.drop_down_list_attr_footerTextSize, defauldFooterTextSize);
//        ta.getDimensionPixelSize(index, defValue)
//        ta.getDimension(index, defValue)
//        ta.getDimensionPixelOffset(index, defValue)
        Log.w(TAG, "getAttrs--isDropDownLoad:"+isHeaderRefresh);
        Log.w(TAG, "getAttrs--isOnBottomLoad:"+isFooterRefresh);
        ta.recycle();
    }
	
	private void init(Context conext){
		this.context = conext;
	    this.setCacheColorHint(Color.TRANSPARENT);
	    initHeader();
	    initFooter();
	    this.setOnScrollListener(this);
	}
	
	private void initHeader() {
		if (!isHeaderRefresh) {
			return;
		}
		flipAnimation = new RotateAnimation(0, 180,
				Animation.RELATIVE_TO_SELF, 0.5F,
				Animation.RELATIVE_TO_SELF, 0.5f);
		flipAnimation.setInterpolator(new LinearInterpolator());
		flipAnimation.setDuration(250);
		flipAnimation.setFillAfter(true);

		reverseFlipAnimation = new RotateAnimation(180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		reverseFlipAnimation.setInterpolator(new LinearInterpolator());
		reverseFlipAnimation.setDuration(250);
		reverseFlipAnimation.setFillAfter(true);

		headerDefaultStr = context.getString(R.string.header_default_text);
		headerPullStr = context.getString(R.string.header_pull_text);
		headerReleaseStr = context.getString(R.string.header_release_text);
		headerLoadingStr = context.getString(R.string.header_loading_text);

		if (headerLayout == null) {
			headerLayout = LayoutInflater.from(context).inflate(
					R.layout.header_view, null);
		}

		headerText = (TextView) headerLayout
				.findViewById(R.id.drop_down_list_header_default_text);
		headerImage = (ImageView) headerLayout
				.findViewById(R.id.drop_down_list_header_image);
		headerProgressBar = (ProgressBar) headerLayout
				.findViewById(R.id.drop_down_list_header_progress_bar);
		headerSecondText = (TextView) headerLayout
				.findViewById(R.id.drop_down_list_header_second_text);
		if (drowDownDrawable != null) {
			headerImage.setImageDrawable(drowDownDrawable);
		}
		if (headerProgressBarIndeterminateDrawable != null) {
			headerProgressBar
					.setIndeterminateDrawable(headerProgressBarIndeterminateDrawable);
		}

		headerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, headerTextSize);
		headerSecondText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				headerSecondTextSize);
		headerSecondText.setTextColor(headerSecondTextColor);
		headerText.setTextColor(headerTextColor);

		headerLayout.setClickable(true);
		headerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		headerText.setText(headerDefaultStr);
		headerLayout.measure(-1, -1);
		headContentHeight = headerLayout.getMeasuredHeight();

		headerLayout.setPadding(0, -headContentHeight, 0, 0);
		addHeaderView(headerLayout);
		currentState = DONE;

	}
	
	private void initFooter() {
		Log.e(TAG, "isOnBottomLoad:"+isFooterRefresh);
		if (!isFooterRefresh) {
			return;
		}

		footerMoreStr = context.getString(R.string.footer_default_text);
		footerLoadingStr = context.getString(R.string.footer_loading_text);
		footerNoMoreStr = context.getString(R.string.footer_no_more_text);

		if (footerLayout == null) {
			footerLayout = LayoutInflater.from(context).inflate(
					R.layout.footer_view, null);
		}
		rootView = (RelativeLayout) footerLayout.findViewById(R.id.footer_layout);
		footerText = (TextView) footerLayout.findViewById(R.id.footer_button);
	//	footerButton.setDrawingCacheBackgroundColor(0);
		footerText.setTextColor(footerTextColor);
		footerText.setTextSize(TypedValue.COMPLEX_UNIT_PX, footerTextSize);
//		footerText.setEnabled(false);
		footerProgressBar = (ProgressBar) footerLayout
				.findViewById(R.id.footer_progress_bar);
		if (footerProgressBarIndeterminateDrawable != null) {
			footerProgressBar
					.setIndeterminateDrawable(footerProgressBarIndeterminateDrawable);
		}
		footerLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBottom();
			}
		});
//		footerButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				onBottom();
//			}
//		});
		addFooterView(footerLayout);
	}
	
	/**
	 *
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(isHeaderRefresh){
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(firstItemIndex==0 && !isRecored){
					isRecored = true;
					startY = (int)ev.getY();
//					Log.i(TAG, "ACTION_DOWN----startY:"+startY);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY = (int)ev.getY();
				int distance = tempY-startY;
//				Log.i(TAG, "ACTION_MOVE----startY:"+startY+"   tempY:"+tempY+"    distance:"+distance);
				//??有必要再记录一次开始Y轴坐标吗
//				if(firstItemIndex==0 && !isRecored){
//					isRecored = true;
//					startY = (int)ev.getY();
//				}
				
				if(currentState!=REFRESHING && isRecored){
					if(currentState==DONE){//默认状态
						if(distance>0){
							currentState =PULL_To_REFRESH;
							if(BuildConfig.DEBUG){
//								Log.d(TAG, "ACTION_MOVE    DONE------>PULL_To_REFRESH");
							}
							headerLayout.setPadding(0, -headContentHeight+distance/RATE, 0, 0);
							changeHeaderViewByState(currentState);
						}
					}else if(currentState==PULL_To_REFRESH){//下拉刷新状态
						setSelection(0);
						if(distance/RATE>=headContentHeight){
							// 下拉到可以进入RELEASE_TO_REFRESH的状态
							currentState=RELEASE_To_REFRESH;
							if(BuildConfig.DEBUG){
//								Log.d(TAG, "ACTION_MOVE    PULL_To_REFRESH------>RELEASE_To_REFRESH");
							}
							headerLayout.setPadding(0, -headContentHeight+distance/RATE, 0, 0);
							changeHeaderViewByState(currentState);
						}else if(distance<0){//上推到顶
							currentState=DONE;
							if(BuildConfig.DEBUG){
								Log.d(TAG, "ACTION_MOVE    PULL_To_REFRESH------>DONE");
							}
							headerLayout.setPadding(0, -headContentHeight, 0, 0);
							changeHeaderViewByState(currentState);
						}else{
							headerLayout.setPadding(0, -headContentHeight+distance/RATE, 0, 0);
						}
					}else if(currentState==RELEASE_To_REFRESH){//松开刷新状态
						setSelection(0);
						if(distance/RATE<headContentHeight){
							//上推，回到下拉刷新状态
							currentState = PULL_To_REFRESH;
							isBack = true;
							if(BuildConfig.DEBUG){
//								Log.d(TAG, "ACTION_MOVE    RELEASE_To_REFRESH------>PULL_To_REFRESH");
							}
							headerLayout.setPadding(0, -headContentHeight+distance/RATE, 0, 0);
							changeHeaderViewByState(currentState);
						}else if(distance<=0){
							//上推到顶，回到DONE（默认）状态
							currentState =DONE;
							if(BuildConfig.DEBUG){
//								Log.d(TAG, "ACTION_MOVE    RELEASE_To_REFRESH------>DONE");
							}
							headerLayout.setPadding(0, -headContentHeight, 0, 0);
							changeHeaderViewByState(currentState);
						}else{
							headerLayout.setPadding(0, -headContentHeight+distance/RATE, 0, 0);
						}
						
					}else if(currentState==REFRESHING){//刷新或加载状态
						//Move不会引起REFRESHING
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if(currentState!=REFRESHING){
					if(currentState==PULL_To_REFRESH){
						currentState = DONE;
						if(BuildConfig.DEBUG){
//							Log.d(TAG, "ACTION_UP   PULL_To_REFRESH------>DONE");
						}
						headerLayout.setPadding(0,-headContentHeight, 0, 0);
						changeHeaderViewByState(currentState);
					}else if(currentState==RELEASE_To_REFRESH){
						currentState = REFRESHING;
						if(BuildConfig.DEBUG){
//							Log.d(TAG, "ACTION_UP   RELEASE_To_REFRESH------>REFRESHING");
						}
						if(onHeaderRefreshListener!=null){
							onHeaderRefreshListener.onHeaderRefresh();
						}
						headerLayout.setPadding(0, 0, 0, 0);
						changeHeaderViewByState(currentState);
					}else if(currentState==DONE){
						//不需要做任何
					}
				}
				
				isBack = false;
				isRecored = false;
				break;
			default:
				break;
			}
		}
		return super.onTouchEvent(ev);
	}
	
	public void changeHeaderViewByState(int state){
		switch (state) {
		case DONE:
			headerText.setText(headerDefaultStr);
			headerProgressBar.setVisibility(View.GONE);
			headerImage.clearAnimation();
			headerImage.setVisibility(View.VISIBLE);
			break;
		case PULL_To_REFRESH:
			headerText.setText(headerPullStr);
			headerProgressBar.setVisibility(View.GONE);
			headerImage.clearAnimation();
			headerImage.setVisibility(View.VISIBLE);
			if(isBack){
				isBack = false;
				headerImage.startAnimation(reverseFlipAnimation);
			}
			break;
		case RELEASE_To_REFRESH:
			headerText.setText(headerReleaseStr);
			headerProgressBar.setVisibility(View.GONE);
			headerImage.clearAnimation();
			headerImage.startAnimation(flipAnimation);
			headerImage.setVisibility(View.VISIBLE);
			break;
		case REFRESHING:
			headerText.setText(headerLoadingStr);
			headerProgressBar.setVisibility(View.VISIBLE);
			headerImage.clearAnimation();
			headerImage.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public void onHeaderRefreshComplete(){
		currentState = DONE;
		headerLayout.setPadding(0,-headContentHeight, 0, 0);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MM-dd HH:mm:ss");
		headerSecondText.setText(getResources().getString(R.string.update_at)
				+ dateFormat.format(new Date()));
		changeHeaderViewByState(currentState);
	}
	
	public void onFooterRefreshComplete(){
		if(isFooterRefresh){
			footerProgressBar.setVisibility(View.GONE);
			if(!hasMore){
				footerText.setText(footerNoMoreStr);
				footerLayout.setEnabled(false);
				if(!isShowFooterWhenNoMore){
					removeFooterView(footerLayout);
				}
			}else{
				footerText.setText(footerMoreStr);
				footerLayout.setEnabled(true);
			}
		}
		isOnFooterLoading = false;
	}
	
	private void onBottom(){
//		Log.i("TAG", "onBottom--------");
		if(isFooterRefresh && !isOnFooterLoading){
//			Log.i("TAG", "onBottom--------2222222222");
			isOnFooterLoading = true; 
			onBottomBeginRefresh();
			onFooterRefreshListener.onFooterRefresh();
		}
	}
	
	private void onBottomBeginRefresh(){
		if(isFooterRefresh){
			if(isShowFooterProgressBar){
				footerProgressBar.setVisibility(View.VISIBLE);
			}
			footerText.setText(footerLoadingStr);
//			footerLayout.setEnabled(true);
		}
	}
	
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisibleItem;
		if(isFooterRefresh && isAutoFooterRefresh && hasMore){
			if(firstVisibleItem>0 && totalItemCount>0 && (firstVisibleItem+visibleItemCount==totalItemCount)){
				onBottom();
			}
		}

	}
	
	
	
	
//	/**
//	 * @return the isDropDownLoad
//	 */
//	public boolean isDropDownLoad() {
//		return isHeaderRefresh;
//	}
//
//	/**
//	 * @param isDropDownLoad the isDropDownLoad to set
//	 */
//	public void setDropDownLoad(boolean isDropDownLoad) {
//		this.isHeaderRefresh = isDropDownLoad;
//	}
//
//	/**
//	 * @return the isOnBottomLoad
//	 */
//	public boolean isOnBottomLoad() {
//		return isFooterRefresh;
//	}
//
//	/**
//	 * @param isOnBottomLoad the isOnBottomLoad to set
//	 */
//	public void setOnBottomLoad(boolean isOnBottomLoad) {
//		this.isFooterRefresh = isOnBottomLoad;
//	}
//
//	/**
//	 * @return the isAutoLoadOnBottom
//	 */
//	public boolean isAutoLoadOnBottom() {
//		return isAutoFooterRefresh;
//	}
//
//	/**
//	 * @param isAutoLoadOnBottom the isAutoLoadOnBottom to set
//	 */
//	public void setAutoLoadOnBottom(boolean isAutoLoadOnBottom) {
//		this.isAutoFooterRefresh = isAutoLoadOnBottom;
//	}
	
	

	/**
	 * @return the drowDownDrawable
	 */
	public Drawable getDrowDownDrawable() {
		return drowDownDrawable;
	}

	/**
	 * @return the isHeaderRefresh
	 */
	public boolean isHeaderRefresh() {
		return isHeaderRefresh;
	}

	/**
	 * @param isHeaderRefresh the isHeaderRefresh to set
	 */
	public void setHeaderRefresh(boolean isHeaderRefresh) {
		this.isHeaderRefresh = isHeaderRefresh;
	}

	/**
	 * @return the isFooterRefresh
	 */
	public boolean isFooterRefresh() {
		return isFooterRefresh;
	}

	/**
	 * @param isFooterRefresh the isFooterRefresh to set
	 */
	public void setFooterRefresh(boolean isFooterRefresh) {
		this.isFooterRefresh = isFooterRefresh;
	}
	

	/**
	 * @return the isAutoFooterRefresh
	 */
	public boolean isAutoFooterRefresh() {
		return isAutoFooterRefresh;
	}

	/**
	 * @param isAutoFooterRefresh the isAutoFooterRefresh to set
	 */
	public void setAutoFooterRefresh(boolean isAutoFooterRefresh) {
		this.isAutoFooterRefresh = isAutoFooterRefresh;
	}

	/**
	 * @param drowDownDrawable the drowDownDrawable to set
	 */
	public void setDrowDownDrawable(Drawable drowDownDrawable) {
		this.drowDownDrawable = drowDownDrawable;
	}

	/**
	 * @return the headerTextColor
	 */
	public int getHeaderTextColor() {
		return headerTextColor;
	}

	/**
	 * @param headerTextColor the headerTextColor to set
	 */
	public void setHeaderTextColor(int headerTextColor) {
		this.headerTextColor = headerTextColor;
	}

	/**
	 * @return the headerSecondTextColor
	 */
	public int getHeaderSecondTextColor() {
		return headerSecondTextColor;
	}

	/**
	 * @param headerSecondTextColor the headerSecondTextColor to set
	 */
	public void setHeaderSecondTextColor(int headerSecondTextColor) {
		this.headerSecondTextColor = headerSecondTextColor;
	}

	/**
	 * @return the headerSecondTextSize
	 */
	public int getHeaderSecondTextSize() {
		return headerSecondTextSize;
	}

	/**
	 * @param headerSecondTextSize the headerSecondTextSize to set
	 */
	public void setHeaderSecondTextSize(int headerSecondTextSize) {
		this.headerSecondTextSize = headerSecondTextSize;
	}

	/**
	 * @return the headerDefaultStr
	 */
	public String getHeaderDefaultStr() {
		return headerDefaultStr;
	}

	/**
	 * @param headerDefaultStr the headerDefaultStr to set
	 */
	public void setHeaderDefaultStr(String headerDefaultStr) {
		this.headerDefaultStr = headerDefaultStr;
	}

	/**
	 * @return the headerPullStr
	 */
	public String getHeaderPullStr() {
		return headerPullStr;
	}

	/**
	 * @param headerPullStr the headerPullStr to set
	 */
	public void setHeaderPullStr(String headerPullStr) {
		this.headerPullStr = headerPullStr;
	}

	/**
	 * @return the headerReleaseStr
	 */
	public String getHeaderReleaseStr() {
		return headerReleaseStr;
	}

	/**
	 * @param headerReleaseStr the headerReleaseStr to set
	 */
	public void setHeaderReleaseStr(String headerReleaseStr) {
		this.headerReleaseStr = headerReleaseStr;
	}

	/**
	 * @return the headerLoadingStr
	 */
	public String getHeaderLoadingStr() {
		return headerLoadingStr;
	}

	/**
	 * @param headerLoadingStr the headerLoadingStr to set
	 */
	public void setHeaderLoadingStr(String headerLoadingStr) {
		this.headerLoadingStr = headerLoadingStr;
	}


	/**
	 * @return the onHeaderRefreshListener
	 */
	public OnHeaderRefreshListener getOnHeaderRefreshListener() {
		return onHeaderRefreshListener;
	}

	/**
	 * @param onHeaderRefreshListener the onHeaderRefreshListener to set
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener onHeaderRefreshListener) {
		this.onHeaderRefreshListener = onHeaderRefreshListener;
	}

	/**
	 * @return the onFooterRefreshListener
	 */
	public OnFooterRefreshListener getOnFooterRefreshListener() {
		return onFooterRefreshListener;
	}

	/**
	 * @param onFooterRefreshListener the onFooterRefreshListener to set
	 */
	public void setOnFooterRefreshListener(
			OnFooterRefreshListener onFooterRefreshListener) {
		this.onFooterRefreshListener = onFooterRefreshListener;
	}

	/**
	 * @return the hasMore
	 */
	public boolean isHasMore() {
		return hasMore;
	}

	/**
	 * @param hasMore the hasMore to set
	 */
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	
	









	/**
	 * @return the headerProgressBarIndeterminateDrawable
	 */
	public Drawable getHeaderProgressBarIndeterminateDrawable() {
		return headerProgressBarIndeterminateDrawable;
	}

	/**
	 * @param headerProgressBarIndeterminateDrawable the headerProgressBarIndeterminateDrawable to set
	 */
	public void setHeaderProgressBarIndeterminateDrawable(
			Drawable headerProgressBarIndeterminateDrawable) {
		this.headerProgressBarIndeterminateDrawable = headerProgressBarIndeterminateDrawable;
	}

	/**
	 * @return the footerProgressBarIndeterminateDrawable
	 */
	public Drawable getFooterProgressBarIndeterminateDrawable() {
		return footerProgressBarIndeterminateDrawable;
	}

	/**
	 * @param footerProgressBarIndeterminateDrawable the footerProgressBarIndeterminateDrawable to set
	 */
	public void setFooterProgressBarIndeterminateDrawable(
			Drawable footerProgressBarIndeterminateDrawable) {
		this.footerProgressBarIndeterminateDrawable = footerProgressBarIndeterminateDrawable;
	}












	/**
	 * @return the headerTextSize
	 */
	public int getHeaderTextSize() {
		return headerTextSize;
	}

	/**
	 * @param headerTextSize the headerTextSize to set
	 */
	public void setHeaderTextSize(int headerTextSize) {
		this.headerTextSize = headerTextSize;
	}

	/**
	 * @return the footerTextColor
	 */
	public int getFooterTextColor() {
		return footerTextColor;
	}

	/**
	 * @param footerTextColor the footerTextColor to set
	 */
	public void setFooterTextColor(int footerTextColor) {
		this.footerTextColor = footerTextColor;
	}

	/**
	 * @return the footerTextSize
	 */
	public int getFooterTextSize() {
		return footerTextSize;
	}

	/**
	 * @param footerTextSize the footerTextSize to set
	 */
	public void setFooterTextSize(int footerTextSize) {
		this.footerTextSize = footerTextSize;
	}

	/**
	 * @return the headerText
	 */
	public TextView getHeaderText() {
		return headerText;
	}

	/**
	 * @param headerText the headerText to set
	 */
	public void setHeaderText(TextView headerText) {
		this.headerText = headerText;
	}












	/**
	 * 
	 * 项目名称:seaway-android-common
	 * 类名称:OnHeaderRefreshListener
	 * 类描述：顶部刷新监听事件
	 * 创建人：xubt
	 * 创建时间:2014-4-4 上午10:27:58
	 * -------------------------------修订历史--------------------------
	 * 修改人：xubt
	 * 修改时间:2014-4-4 上午10:27:58
	 * 修改备注：
	 * @version：
	 */
	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh();
	}
	
	/**
	 * 
	 * 项目名称:seaway-android-common
	 * 类名称:OnFooterRefreshListener
	 * 类描述：底部刷新监听事件
	 * 创建人：xubt
	 * 创建时间:2014-4-4 上午10:28:47
	 * -------------------------------修订历史--------------------------
	 * 修改人：xubt
	 * 修改时间:2014-4-4 上午10:28:47
	 * 修改备注：
	 * @version：
	 */
	public interface OnFooterRefreshListener{
		public void onFooterRefresh();
	}

}
