/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.dom.motor;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.MotorMenu",
        repositoryFor = Motor.class
)
@DomainServiceLayout(
        named = "Motores",
        menuOrder = "10"
)
public class MotorRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Motor> listAll() {
        return repositoryService.allInstances(Motor.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Motor> findByName(
            @ParameterLayout(named="TAG")
            final String tag
    ) {
        TypesafeQuery<Motor> q = isisJdoSupport.newTypesafeQuery(Motor.class);
        final QMotor cand = QMotor.candidate();
        q = q.filter(
                cand.tag.indexOf(q.stringParameter("tag")).ne(-1)
        );
        return q.setParameter("tag", tag)
                .executeList();
    }

    @Programmatic
    public Motor findByNameExact(final String tag) {
        TypesafeQuery<Motor> q = isisJdoSupport.newTypesafeQuery(Motor.class);
        final QMotor cand = QMotor.candidate();
        q = q.filter(
                cand.tag.eq(q.stringParameter("tag"))
        );
        return q.setParameter("tag", tag)
                .executeUnique();
    }

    @Programmatic
    public void ping() {
        TypesafeQuery<Motor> q = isisJdoSupport.newTypesafeQuery(Motor.class);
        final QMotor candidate = QMotor.candidate();
        q.range(0,2);
        q.orderBy(candidate.tag.asc());
        q.executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<MotorRepositorio> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named = "Crear motor")
    @MemberOrder(sequence = "3")
    public Motor create(
            final @ParameterLayout(named="TAG") String tag,
            final @ParameterLayout(named="Temp Aceite") double tempAceite,
            final @ParameterLayout(named="Temp Agua") double tempAgua
            ) {
        return repositoryService.persist(new Motor(tag, tempAceite,tempAgua));
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
