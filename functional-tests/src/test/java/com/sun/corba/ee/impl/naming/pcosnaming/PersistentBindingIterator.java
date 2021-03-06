/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.corba.ee.impl.naming.pcosnaming;

// Import general CORBA classes
import org.omg.CORBA.INTERNAL;

import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableServer.POA;

import com.sun.corba.ee.impl.naming.cosnaming.BindingIteratorImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class TransientBindingIterator implements the abstract methods
 * defined by BindingIteratorImpl, to use with the TransientNamingContext
 * implementation of the NamingContextImpl. The TransientBindingIterator
 * implementation receives a hash table of InternalBindingValues, and uses
 * an Enumeration to iterate over the contents of the hash table.
 * @see BindingIteratorImpl
 * @see TransientNamingContext
 */
public class PersistentBindingIterator extends BindingIteratorImpl
{
    private POA biPOA;
    /**
     * Constructs a new PersistentBindingIterator object.
     * @param orb a org.omg.CORBA.ORB object.
     * @param aTable A hashtable containing InternalBindingValues which is
     * the content of the PersistentNamingContext.
     * initialize.
     * @param thePOA the POA to use.
     * @throws java.lang.Exception can throw many exceptions?
     */
    public PersistentBindingIterator(org.omg.CORBA.ORB orb, 
        Map<InternalBindingKey,InternalBindingValue> aTable,
        POA thePOA ) throws java.lang.Exception
    {
        super(orb);
        map = new HashMap<InternalBindingKey,InternalBindingValue>( aTable ) ;
        iterator = this.map.keySet().iterator() ;
        currentSize = this.map.size();
        biPOA = thePOA;
    }

    /**
   * Returns the next binding in the NamingContext. Uses the enumeration
   * object to determine if there are more bindings and if so, returns
   * the next binding from the InternalBindingValue.
   * @param b The Binding as an out parameter.
   * @return true if there were more bindings.
   */
    final public boolean nextOneImpl(org.omg.CosNaming.BindingHolder b)
    {
        // If there are more elements get the next element
        boolean hasMore = iterator.hasNext();
        if (hasMore) {
            InternalBindingKey theBindingKey = iterator.next();
            InternalBindingValue theElement = map.get( theBindingKey );
            NameComponent n = new NameComponent( theBindingKey.id,
                theBindingKey.kind );
            NameComponent[] nlist = new NameComponent[1];
            nlist[0] = n;
            BindingType theType = theElement.theBindingType;
            
            b.value =
                new Binding( nlist, theType );  
        } else {
            // Return empty but marshalable binding
            b.value = new Binding(new NameComponent[0],BindingType.nobject);
        }
        return hasMore;
    }

    /**
   * Destroys this BindingIterator by disconnecting from the ORB
   * @exception org.omg.CORBA.SystemException One of a fixed set of CORBA system exceptions.
   */
    final public void destroyImpl()
    {
        // Remove the object from the Active Object Map.
        try {
            byte[] objectId = biPOA.servant_to_id( this );
            if( objectId != null ) {
                biPOA.deactivate_object( objectId );
            }
        }
        catch( Exception e ) {
            throw new INTERNAL( "Exception in BindingIterator.Destroy " + e );
        }
    }

    /**
   * Returns the remaining number of elements in the iterator.
   * @return the remaining number of elements in the iterator.   
   */
    public final int remainingElementsImpl() {
        return currentSize;
    }

    private int currentSize;
    private HashMap<InternalBindingKey,InternalBindingValue> map ;
    private Iterator<InternalBindingKey> iterator;
}
