/*******************************************************************************
 * Copyright (C) 2005-2013 Alfresco Software Limited.
 *  
 *  This file is part of Alfresco Mobile for Android.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package org.alfresco.mobile.android.application.operations.batch.node.favorite;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.mobile.android.api.model.Folder;
import org.alfresco.mobile.android.api.model.Node;
import org.alfresco.mobile.android.application.operations.batch.node.NodeOperationRequest;

import android.database.Cursor;

public class FavoriteNodeRequest extends NodeOperationRequest
{
    private static final long serialVersionUID = 1L;
    
    public static final int TYPE_ID = 60;
    
    private Boolean markFavorite;

    private boolean batchFavorite = false;
    
    private static final String PROP_FAVORITE = "favorite";

    private static final String PROP_BATCH_FAVORITE = "batchFavorite";


    // ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    // ///////////////////////////////////////////////////////////////////////////
    public FavoriteNodeRequest(String parentIdentifier, String documentIdentifier)
    {
        super(parentIdentifier, documentIdentifier);
        requestTypeId = TYPE_ID;
    }
    
    public FavoriteNodeRequest(Folder parent, Node node)
    {
        this(parent.getIdentifier(), node.getIdentifier());
        setNotificationTitle(node.getName());
        setMimeType(node.getName());
    }
    
    public FavoriteNodeRequest(String parentIdentifier, String documentIdentifier, boolean markFavorite)
    {
        this(parentIdentifier, documentIdentifier);
        this.markFavorite = markFavorite;
        
        persistentProperties = new HashMap<String, Serializable>();
        persistentProperties.put(PROP_FAVORITE, markFavorite);
    }
    
    public FavoriteNodeRequest(String parentIdentifier, String documentIdentifier, boolean markFavorite, boolean batchFavorite)
    {
        this(parentIdentifier, documentIdentifier);
        this.markFavorite = markFavorite;
        this.batchFavorite = batchFavorite;
        
        persistentProperties = new HashMap<String, Serializable>();
        persistentProperties.put(PROP_FAVORITE, markFavorite);
        persistentProperties.put(PROP_BATCH_FAVORITE, batchFavorite);
    }
    
    public FavoriteNodeRequest(Folder parent, Node node, boolean markFavorite, boolean batchFavorite)
    {
        this(parent.getIdentifier(), node.getIdentifier(), markFavorite, batchFavorite);
        setNotificationTitle(node.getName());
        setMimeType(node.getName());
    }
    
    public FavoriteNodeRequest(Folder parent, Node node, boolean markFavorite)
    {
        this(parent.getIdentifier(), node.getIdentifier(), markFavorite);
        setNotificationTitle(node.getName());
        setMimeType(node.getName());
    }

    public FavoriteNodeRequest(Cursor cursor)
    {
        super(cursor);
        requestTypeId = TYPE_ID;

        Map<String, String> tmpProperties = retrievePropertiesMap(cursor);
        if (tmpProperties.containsKey(PROP_FAVORITE))
        {
            this.markFavorite = Boolean.parseBoolean(tmpProperties.remove(PROP_FAVORITE));
        }
        
        if (tmpProperties.containsKey(PROP_BATCH_FAVORITE))
        {
            this.batchFavorite = Boolean.parseBoolean(tmpProperties.remove(PROP_BATCH_FAVORITE));
        }
    }

    // ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    // ///////////////////////////////////////////////////////////////////////////
    public Boolean markAsFavorite()
    {
        return markFavorite;
    }
    
    public Boolean isBatch()
    {
        return batchFavorite;
    }
}
