/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package endpoint;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(
    name="Subtractor",
    serviceName="SubtractorService",
    targetNamespace="http://example.com/Subtractor"
)
public class Subtractor {
	static int count = 0;
	public Subtractor() {}

	@WebMethod(operationName="add", action="urn:Add")
	public int add(int i, int j) {
                int k = i -j ;
		count++;
                System.out.println(i + "-" + j +" = " + k);
 		System.out.println("Count is: "+count);
		return k;
	}
}
