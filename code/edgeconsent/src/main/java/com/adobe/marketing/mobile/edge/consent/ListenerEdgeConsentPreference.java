/*
  Copyright 2021 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.edge.consent;

import com.adobe.marketing.mobile.Event;
import com.adobe.marketing.mobile.ExtensionApi;
import com.adobe.marketing.mobile.ExtensionListener;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;

class ListenerEdgeConsentPreference extends ExtensionListener {

	/**
	 * Constructor.
	 *
	 * @param extensionApi an instance of {@link ExtensionApi}
	 * @param type         the {@link String} eventType this listener is registered to handle
	 * @param source       the {@link String} eventSource this listener is registered to handle
	 */
	ListenerEdgeConsentPreference(final ExtensionApi extensionApi, final String type, final String source) {
		super(extensionApi, type, source);
	}

	/**
	 * Method that gets called when event with event type {@link ConsentConstants.EventType#EDGE}
	 * and with event source {@link ConsentConstants.EventSource#CONSENT_PREFERENCE}  is dispatched through eventHub.
	 *
	 * @param event the edge request {@link Event} to be processed
	 */
	@Override
	public void hear(final Event event) {
		if (event == null || event.getEventData() == null || event.getEventData().isEmpty()) {
			MobileCore.log(
				LoggingMode.DEBUG,
				ConsentConstants.LOG_TAG,
				"ListenerEdgeConsentPreference - Event or Event data is null. Ignoring the event."
			);
			return;
		}

		final ConsentExtension parentExtension = getConsentExtension();

		if (parentExtension == null) {
			MobileCore.log(
				LoggingMode.DEBUG,
				ConsentConstants.LOG_TAG,
				"ListenerEdgeConsentPreference - The parent extension associated with this listener is null, ignoring the event."
			);
			return;
		}

		parentExtension.handleEdgeConsentPreferenceHandle(event);
	}

	/**
	 * Returns the parent extension associated with the listener.
	 *
	 * @return a {@link ConsentExtension} object registered with the eventHub
	 */
	ConsentExtension getConsentExtension() {
		return (ConsentExtension) getParentExtension();
	}
}
