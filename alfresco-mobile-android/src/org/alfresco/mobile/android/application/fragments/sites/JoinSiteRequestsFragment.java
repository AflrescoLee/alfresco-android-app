/*******************************************************************************
 * Copyright (C) 2005-2013 Alfresco Software Limited.
 * 
 * This file is part of Alfresco Mobile for Android.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.alfresco.mobile.android.application.fragments.sites;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.mobile.android.api.asynchronous.JoinSiteRequestsLoader;
import org.alfresco.mobile.android.api.asynchronous.LoaderResult;
import org.alfresco.mobile.android.api.model.Site;
import org.alfresco.mobile.android.api.model.impl.PagingResultImpl;
import org.alfresco.mobile.android.application.R;
import org.alfresco.mobile.android.application.utils.SessionUtils;
import org.alfresco.mobile.android.ui.fragments.BaseListFragment;

import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * This Fragment is responsible to display the list of join site request. <br/>
 * 
 * @author Jean Marie Pascal
 */
public class JoinSiteRequestsFragment extends BaseListFragment implements LoaderCallbacks<LoaderResult<List<Site>>>
{
    /** Public Fragment TAG. */
    public static final String TAG = "JoinSiteRequestsFragment";

    public JoinSiteRequestsFragment()
    {
        loaderId = JoinSiteRequestsLoader.ID;
        callback = this;
        emptyListMessageId = R.string.empty_joinsiterequest;
    }

    public static JoinSiteRequestsFragment newInstance(Bundle b)
    {
        JoinSiteRequestsFragment fr = new JoinSiteRequestsFragment();
        fr.setArguments(b);
        return fr;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.sdk_list, container, false);

        init(v, emptyListMessageId);
        
        setListShown(false);

        return v;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        alfSession = SessionUtils.getSession(getActivity());
        SessionUtils.checkSession(getActivity(), alfSession);

        title = getString(R.string.joinsiterequest_list_title);
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setTitle(title);

        return d;
    }

    @Override
    public Loader<LoaderResult<List<Site>>> onCreateLoader(int id, Bundle args)
    {
        return new JoinSiteRequestsLoader(getActivity(), alfSession);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult<List<Site>>> loader, LoaderResult<List<Site>> results)
    {
        if (adapter == null)
        {
            adapter = new JoinSiteRequestAdapter(this, R.layout.app_list_button_row, new ArrayList<Site>(0));
        }
        if (checkException(results))
        {
            onLoaderException(results.getException());
        }
        else
        {
            displayPagingData(new PagingResultImpl<Site>(results.getData(), false, results.getData().size()), loaderId,
                    callback);
        }
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult<List<Site>>> loader)
    {
        // Do Nothing
    }

    @SuppressWarnings("unchecked")
    public void remove(Site site)
    {
        if (adapter != null)
        {
            ((ArrayAdapter<Site>) adapter).remove(site);
            if (adapter.isEmpty())
            {
                displayEmptyView();
            }
        }
    }

}
