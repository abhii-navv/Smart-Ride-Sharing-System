package Uber.ride;

/**
 * Interface for all service providers in the Uber system
 * This interface defines common behavior for entities that can provide services
 */
public interface ServiceProvider {
    /**
     * Gets the unique identifier for this service provider
     * @return The unique identifier
     */
    String getId();
    
    /**
     * Gets the name of this service provider
     * @return The provider's name
     */
    String getName();
    
    /**
     * Gets the current wallet balance of this service provider
     * @return The current wallet balance
     */
    double getWallet();
    
    /**
     * Sets the wallet balance for this service provider
     * @param amount The new wallet balance
     */
    void setWallet(double amount);
    
    /**
     * Displays info about this service provider
     */
    void displayInfo();
    
    /**
     * Determines if this provider is available to offer services
     * @return true if available, false otherwise
     */
    boolean isAvailable();
}
