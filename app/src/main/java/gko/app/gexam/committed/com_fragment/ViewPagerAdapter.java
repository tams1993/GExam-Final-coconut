package gko.app.gexam.committed.com_fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.style.ImageSpan;

import gko.app.gexam.R;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int icons[] = {R.drawable.ic_action_paste,R.drawable.ic_action_info,R.drawable.ic_action_group};
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, int mIcons[]) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.icons = mIcons;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            RuleFragment tabRule = new RuleFragment();
            return tabRule;
        }
        else if (position == 1)       // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {

            DetailFragment tabDetail = new DetailFragment();
            return tabDetail;
        } else {

            StudentListFragment tabStudentList = new StudentListFragment();
            return tabStudentList;

        }


    }

    // This method return the titles for the Tabs in the Tab Strip


    @Override
    public CharSequence getPageTitle(int position) {




        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}