/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id: RepeaterBean.java,v 1.3 2004/11/14 07:33:17 tcfujii Exp $
 */

package demo.model;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p>Backing file bean for <code>/repeater.jsp</code> demo.</p>
 */

public class RepeaterBean {


    // -------------------------------------------------------- Bound Components


    /**
     * <p>The <code>accountId</code> field for the current row.</p>
     */
    private UIInput accountId = null;


    public UIInput getAccountId() {
        return accountId;
    }


    public void setAccountId(UIInput accountId) {
        this.accountId = accountId;
    }


    /**
     * <p>The <code>checked</code> field for the current row.</p>
     */
    private UISelectBoolean checked = null;


    public UISelectBoolean getChecked() {
        return checked;
    }


    public void setChecked(UISelectBoolean checked) {
        this.checked = checked;
    }


    /**
     * <p>The <code>created</code> field for the current row.</p>
     */
    private UISelectBoolean created = null;


    public UISelectBoolean getCreated() {
        return created;
    }


    public void setCreated(UISelectBoolean created) {
        this.created = created;
    }


    /**
     * <p>The <code>UIData</code> component representing the entire table.</p>
     */
    private UIData data = null;


    public UIData getData() {
        return data;
    }


    public void setData(UIData data) {
        this.data = data;
    }


    // --------------------------------------------------- Calculated Properties


    /**
     * <p>Return the customer list containing the data backing this demo.</p>
     */
    public List getCustomers() {

        List list = (List)
            FacesContext.getCurrentInstance().getExternalContext().
            getSessionMap().get("list");
        if (list == null) {
            list = new ArrayList();
            list.add(new CustomerBean("123456", "Alpha Beta Company",
                                      "ABC", 1234.56));
            list.add(new CustomerBean("445566", "General Services, Ltd.",
                                      "GS", 33.33));
            list.add(new CustomerBean("654321", "Summa Cum Laude, Inc.",
                                      "SCL", 76543.21));
            list.add(new CustomerBean("333333", "Yabba Dabba Doo", "YDD",
                                      333.33));
            for (int i = 10; i < 20; i++) {
                list.add(new CustomerBean("8888" + i,
                                          "Customer " + i,
                                          "CU" + i,
                                          ((double) i) * 10.0));
            }
            FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("list", list);
        }
        return (list);

    }


    /**
     * <p>Return a calculated label for the per-row button.</p>
     */
    public String getPressLabel() {

        return ("Account " + accountId.getValue());

    }



    // --------------------------------------------------------- Action Handlers


    /**
     * <p>Acknowledge that a row-specific link was clicked.</p>
     */
    public String click() {

        append("Link clicked for account " + accountId.getValue());
        clear();
        return (null);

    }


    /**
     * <p>Create a new empty row to be filled in for a new record
     * in the database.</p>
     */
    public String create() {

        append("CREATE NEW ROW button pressed");
        clear();

        // Add a new row to the table
        List list = getCustomers();
        if (list != null) {
            CustomerBean customer = new CustomerBean();
            list.add(customer);
            int index = data.getRowIndex();
            data.setRowIndex(list.size() - 1);
            created.setSelected(true);
            data.setRowIndex(index);
        }

        // Position so that the new row is visible if necessary
        scroll(list.size());
        return (null);

    }


    /**
     * <p>Delete any customers who have been checked from the list.</p>
     */
    public String delete() {

        append("DELETE CHECKED button pressed");

        // Delete customers for whom the checked field is selected
        List removes = new ArrayList();
        int n = data.getRowCount();
        for (int i = 0; i < n; i++) {
            data.setRowIndex(i);
            if (checked.isSelected()) {
                removes.add(data.getRowData());
                checked.setSelected(false);
                created.setSelected(false);
            }
        }
        if (removes.size() > 0) {
            List list = getCustomers();
            Iterator remove = removes.iterator();
            while (remove.hasNext()) {
                list.remove(remove.next());
            }
        }

        clear();
        return (null);
    }


    /**
     * <p>Scroll directly to the first page.</p>
     */
    public String first() {

        append("FIRST PAGE button pressed");
        scroll(0);
        return (null);

    }


    /**
     * <p>Scroll directly to the last page.</p>
     */
    public String last() {

        append("LAST PAGE button pressed");
        scroll(count() - 1);
        return (null);

    }


    /**
     * <p>Scroll forwards to the next page.</p>
     */
    public String next() {

        append("NEXT PAGE button pressed");
        int first = data.getFirst();
        scroll(first + data.getRows());
        return (null);

    }


    /**
     * <p>Acknowledge that a row-specific button was pressed.</p>
     */
    public String press() {

        append("Button pressed for account " + accountId.getValue());
        clear();
        return (null);

    }


    /**
     * <p>Scroll backwards to the previous page.</p>
     */
    public String previous() {

        append("PREVIOUS PAGE button pressed");
        int first = data.getFirst();
        scroll(first - data.getRows());
        return (null);

    }


    /**
     * <p>Handle a "reset" button by clearing local component values.</p>
     */
    public String reset() {

        append("RESET CHANGES button pressed");
        clear();
        return (null);

    }


    /**
     * <p>Save any changes to the underlying database.  In a real application
     * this would need to distinguish between inserts and updates, based on
     * the state of the "created" property.</p>
     */
    public String update() {

        append("SAVE CHANGES button pressed");
        ; // Save to database as necessary
        clear();
        created();
        return (null);

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>Append an informational message to the set of messages that will
     * be rendered when this view is redisplayed.</p>
     *
     * @param message Message text to be added
     */
    private void append(String message) {

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null,
                           new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            message, null));

    }


    /**
     * <p>Clear the checked state for all customers.</p>
     */
    private void clear() {

        int n = count();
        for (int i = 0; i < n; i++) {
            data.setRowIndex(i);
            checked.setSelected(false);
        }

    }


    /**
     * <p>Return the actual row count from our underlying data model.</p>
     */
    private int count() {

        int n = data.getRowCount();
        if (n >= 0) {
            return (n);
        }
        n = -1;
        while (true) {
            data.setRowIndex(n++);
            if (!data.isRowAvailable()) {
                break;
            }
        }
        return (n);

    }


    /**
     * <p>Clear the created state of all customers.</p>
     */
    private void created() {

        int n = count();
        for (int i = 0; i < n; i++) {
            data.setRowIndex(i);
            created.setSelected(false);
        }

    }


    /**
     * <p>Return an <code>Iterator</code> over the customer list, if any;
     * otherwise return <code>null</code>.</p>
     */
    private Iterator iterator() {

        List list = getCustomers();
        if (list != null) {
            return (list.iterator());
        } else {
            return (null);
        }

    }


    /**
     * <p>Scroll to the page that contains the specified row number.</p>
     *
     * @param row Desired row number
     */
    private void scroll(int row) {

        int rows = data.getRows();
        if (rows < 1) {
            return; // Showing entire table already
        }
        if (row < 0) {
            data.setFirst(0);
        } else if (row >= count()) {
            data.setFirst(count() - 1);
        } else {
            data.setFirst(row - (row % rows));
        }

    }


}
