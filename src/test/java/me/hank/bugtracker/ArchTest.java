package me.hank.bugtracker;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("me.hank.bugtracker");

        noClasses()
            .that()
                .resideInAnyPackage("me.hank.bugtracker.service..")
            .or()
                .resideInAnyPackage("me.hank.bugtracker.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..me.hank.bugtracker.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
