/**
 * Copyright 2012 Nuvola Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsr.myschool.client.mvp;

import com.gwtplatform.mvp.client.PopupView;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatedPopupView extends PopupView {
    /**
     * Request the popup view to display list of violations sent from server
     * when JSR 303 validation fails
     * <p>
     * Override this method to change the way violations displayed on the popup view
     * </p>
     *
     * @param violations List of violation to process and display on the popup view
     */
    void showErrors(Set<ConstraintViolation<?>> violations);

    /**
     * Request the view to clear all the violations displayed on the popup view
     * <p>
     * Override this method to change the way violation are cleared
     * </p>
     */
    void clearErrors();
}
