# ☕ Lagermester

**Effektiv lagerstyring gjort enkelt**  
En moderne, minimalistisk webapplikasjon for intern lagerkontroll, logging og smart automatisk håndtering av varebeholdning.

---

## 📚 Innhold

- [Om prosjektet](#-om-prosjektet)
- [Funksjoner](#-funksjoner)
- [Teknologier brukt](#-teknologier-brukt)
- [Hvordan kjøre prosjektet](#-hvordan-kjøre-prosjektet)
- [Integrasjon med Magento](#-integrasjon-med-magento)
- [Videre utvikling](#-videre-utvikling)

---

## 🔥 Om prosjektet

Lagermester er en fullstack Spring Boot-applikasjon laget for å erstatte manuell lagerføring og manglende funksjonalitet i Magento. Applikasjonen gir en visuell og praktisk oversikt over varelageret, inkludert utløpsdatoer, anbefalinger til bestilling, og historikk over tidligere ordre.

Prosjektet fokuserer på:
- Brukervennlighet og minimalistisk design
- Effektiv håndtering av beholdning og utgåtte produkter
- Intern kontroll og logging av all aktivitet
- Klar for profesjonell drift og fremtidig utvidelse

---

## ✨ Funksjoner

- ✅ Vis lageroversikt med produktbilde og mengde
- ✅ Legg inn og oppdater varer direkte (inkl. batch med utløpsdato)
- ✅ Automatisk daglig synkronisering av produkter fra Magento
- ✅ Autocomplete og søk etter navn eller SKU
- ✅ Dashboard med lav beholdning og produkter som snart går ut
- ✅ Logger for:
  - 🔄 API-synk med Magento
  - 📧 Behandling av e-poster og leveranser
  - 🧾 Hentede ordre og hva som manglet
  - 🧍 Brukerhandlinger (hvem endret hva)
  - 📦 Bestillinger og forslag til neste innkjøp
- ✅ Admin-panel med status og historikk
- ✅ Ukentlig rapport hver torsdag kl. 09:00 (klargjort)
- ✅ Logging til fil med rotasjon (produksjonsklar)

---

## 🛠 Teknologier brukt

| Teknologi       | Beskrivelse                          |
|------------------|--------------------------------------|
| **Java 17**       | Backend-språk                        |
| **Spring Boot 3** | Hovedrammeverk for applikasjonen     |
| **Thymeleaf**     | HTML-rendering og databinding        |
| **Spring Data JPA** | ORM for databasetilgang           |
| **PostgreSQL**    | Database (eller MySQL, H2 lokalt)    |
| **Bootstrap 5**   | Moderne og responsiv frontend        |
| **RestTemplate**  | Henting av data fra Magento API      |
| **Jackson**       | JSON-parsing                         |
| **Maven**         | Dependency management                |
| **Git / GitHub**  | Versjonskontroll og samarbeid        |

---

## 🚀 Hvordan kjøre prosjektet

1. **Klon repoet:**
```bash
git clone https://github.com/<brukernavn>/Lagermester.git
cd Lagermester
```

🔄 Integrasjon med Magento
Applikasjonen henter produktdata fra https://din-butikk.noxxxxxx

Det brukes Bearer Token for autentisering

Ved import:

Produkter med manglende lagerstatus blir registrert som 0

Produkter som ikke finnes i systemet logges som "mangler"

Fremtidig støtte for:

Hente ordre automatisk

Tolke e-poster med pakkesedler

Foreslå bestillinger automatisk


## 🧠 Videre utvikling

- [ ] 📧 E-post-integrasjon (parse pakkesedler)
- [ ] 🤖 Automatisk forslag til bestilling
- [ ] 🔍 Forbedret søk og filter
- [ ] 🔐 Adminbruker med tilgangsnivåer
- [ ] 📄 Eksport til PDF / Excel
- [ ] ⏰ Automatisk cron-jobb for ukentlig e-post med rapport
- [ ] 📆 Sortering på utløpsdato og antall
- [ ] 🧠 Bedre kategori- og erstatningslogikk (f.eks. kaffe-typer)
