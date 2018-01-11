package com.sun.demo.dubbo;

import java.util.List;

public interface DemoService {
    List<String> getPermissions(Long id);
}
