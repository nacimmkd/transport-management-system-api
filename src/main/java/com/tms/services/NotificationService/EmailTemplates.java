package com.tms.services.NotificationService;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplates {

    public static String getEmailCredentialTemplate(String fullName, String email, String password) {
        return """
            Bonjour %s,

            Votre compte a été créé avec succès par un administrateur.

            Voici vos identifiants de connexion :
            Email : %s
            Mot de passe : %s

            Nous vous recommandons de changer votre mot de passe après votre première connexion.

            Cordialement,
            L'équipe TMS
            """.formatted(fullName, email, password);
    }


    public static String getDeliveryConfirmationCodeTemplate(String clientName, String deliveryCode) {
        return """
            Bonjour %s,

            Votre code unique de confirmation de livraison est :

            %s

            Veuillez communiquer ce code au livreur pour valider la livraison.

            Merci pour votre confiance.
            L'équipe TMS
            """.formatted(clientName, deliveryCode);
    }
}
