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
package domainapp.modules.simple.dom.planta;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.PlantaMenu",
        repositoryFor = Planta.class
)
@DomainServiceLayout(
        named = "Plantas",
        menuOrder = "10"
)
public class PlantaRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar plantas: ")
    @MemberOrder(sequence = "1")
    public List<Planta> listAll() {
        return repositoryService.allInstances(Planta.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar planta: ")
    @MemberOrder(sequence = "2")
    public List<Planta> findByName(
            @ParameterLayout(named="Nombre")
            final String nombre
    ) {
        TypesafeQuery<Planta> q = isisJdoSupport.newTypesafeQuery(Planta.class);
        final QPlanta cand = QPlanta.candidate();
        q = q.filter(
                cand.nombre.indexOf(q.stringParameter("nombre")).ne(-1)
        );
        return q.setParameter("nombre", nombre)
                .executeList();
    }

    @Programmatic
    public Planta findByNameExact(final String nombre) {
        TypesafeQuery<Planta> q = isisJdoSupport.newTypesafeQuery(Planta.class);
        final QPlanta cand = QPlanta.candidate();
        q = q.filter(
                cand.nombre.eq(q.stringParameter("nombre"))
        );
        return q.setParameter("nombre", nombre)
                .executeUnique();
    }

    @Programmatic
    public void ping() {
        TypesafeQuery<Planta> q = isisJdoSupport.newTypesafeQuery(Planta.class);
        final QPlanta candidate = QPlanta.candidate();
        q.range(0,2);
        q.orderBy(candidate.nombre.asc());
        q.executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<PlantaRepositorio> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named = "Crear planta: ")
    @MemberOrder(sequence = "3")
    public Planta create(
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Provincia") String provincia,
            final @ParameterLayout(named="Cliente") String cliente) {
        return repositoryService.persist(new Planta(nombre, provincia, cliente));
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
