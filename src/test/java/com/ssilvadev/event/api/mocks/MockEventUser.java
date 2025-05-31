package com.ssilvadev.event.api.mocks;

import com.ssilvadev.event.api.model.userEvent.EventUser;

public class MockEventUser {
    public EventUser mockEventUser() {
        var mockEvent = new MockEvent();
        var mockUser = new MockUser();

        var event = mockEvent.mockRemoteEventEntity();
        var user = mockUser.mockUserEntity();

        return new EventUser(event, user);
    }
}
