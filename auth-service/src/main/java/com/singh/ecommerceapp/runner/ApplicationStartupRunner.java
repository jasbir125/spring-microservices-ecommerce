package com.singh.ecommerceapp.runner;

import com.singh.ecommerceapp.constant.AppConstants;
import com.singh.ecommerceapp.processor.PermissionEntityProcessor;
import com.singh.ecommerceapp.processor.RoleEntityProcessor;
import com.singh.ecommerceapp.processor.UserEntityProcessor;
import com.singh.ecommerceapp.service.DataLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApplicationStartupRunner implements CommandLineRunner {
    private final UserEntityProcessor userEntityProcessor;
    private final RoleEntityProcessor roleEntityProcessor;
    private final PermissionEntityProcessor permissionEntityProcessor;
    private final DataLoaderService dataLoaderService;

    @Override
    public void run(String... args) throws IOException {
        dataLoaderService.importData(AppConstants.FILE_ROLES_JSON, roleEntityProcessor);
        dataLoaderService.importData(AppConstants.FILE_PERMISSION_JSON,permissionEntityProcessor);
        dataLoaderService.importData(AppConstants.FILE_USERS_JSON, userEntityProcessor);
    }
}