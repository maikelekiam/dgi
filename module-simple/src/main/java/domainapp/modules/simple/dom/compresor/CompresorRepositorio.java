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
package domainapp.modules.simple.dom.compresor;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.CompresorMenu",
        repositoryFor = Compresor.class
)
@DomainServiceLayout(
        named = "Compresores",
        menuOrder = "10"
)
public class CompresorRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Compresor> listAll() {
        return repositoryService.allInstances(Compresor.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Compresor> findByName(
            @ParameterLayout(named="TAG")
            final String tag
    ) {
        TypesafeQuery<Compresor> q = isisJdoSupport.newTypesafeQuery(Compresor.class);
        final QCompresor cand = QCompresor.candidate();
        q = q.filter(
                cand.tag.indexOf(q.stringParameter("tag")).ne(-1)
        );
        return q.setParameter("tag", tag)
                .executeList();
    }

    @Programmatic
    public Compresor findByNameExact(final String tag) {
        TypesafeQuery<Compresor> q = isisJdoSupport.newTypesafeQuery(Compresor.class);
        final QCompresor cand = QCompresor.candidate();
        q = q.filter(
                cand.tag.eq(q.stringParameter("tag"))
        );
        return q.setParameter("tag", tag)
                .executeUnique();
    }

    @Programmatic
    public void ping() {
        TypesafeQuery<Compresor> q = isisJdoSupport.newTypesafeQuery(Compresor.class);
        final QCompresor candidate = QCompresor.candidate();
        q.range(0,2);
        q.orderBy(candidate.tag.asc());
        q.executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<CompresorRepositorio> {}

    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named = "Crear compresor")
    @MemberOrder(sequence = "3")
    public Compresor create(
            final @ParameterLayout(named="TAG") String tag,
            final @ParameterLayout(named="Temp Succion 1") double tempSuccion1,
            final @ParameterLayout(named="ps1") double ps1,
            final @ParameterLayout(named="Temp Succion 2") double tempSuccion2,
            final @ParameterLayout(named="ps2") double ps2,
            final @ParameterLayout(named="Temp Succion 3") double tempSuccion3,
            final @ParameterLayout(named="ps3") double ps3,
            final @ParameterLayout(named="pd") double pd,
            final @ParameterLayout(named="Caudal diario") double caudalDiario
            ) {
        return repositoryService.persist(new Compresor(tag, tempSuccion1,ps1,tempSuccion2,ps2,tempSuccion3,ps3,pd,caudalDiario));
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
