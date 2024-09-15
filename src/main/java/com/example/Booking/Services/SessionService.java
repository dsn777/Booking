package com.example.Booking.Services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final HttpSession httpSession;

    @Autowired
    public SessionService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void setAttribute(String name, Object object) {
        httpSession.setAttribute(name, object);
    }

    public Object getAttribute(String name) {
        return httpSession.getAttribute(name);
    }

    public <T> List<T> getSafeAttributes(String name, Class<T> targetClass) {
        List<?> sourceList = ((List<?>) this.getAttribute(name));
        return sourceList.stream()
                .filter(targetClass::isInstance)
                .map(targetClass::cast)
                .collect(Collectors.toList());
    }

    public void clear() {
        this.httpSession.invalidate();
    }
}
