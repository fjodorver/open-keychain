/*
 * Copyright (C) 2015 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2014 Vincent Breitmoser <v.breitmoser@mugenguild.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sufficientlysecure.keychain.pgp;

import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.sufficientlysecure.keychain.Constants;
import org.sufficientlysecure.keychain.util.Passphrase;

import android.os.Parcel;
import android.os.Parcelable;


public class PgpSignEncryptInputParcel implements Parcelable {

    protected String mVersionHeader = null;
    protected boolean mEnableAsciiArmorOutput = false;
    protected int mCompressionAlgorithm = CompressionAlgorithmTags.UNCOMPRESSED;
    protected long[] mEncryptionMasterKeyIds = null;
    protected Passphrase mSymmetricPassphrase = null;
    protected int mSymmetricEncryptionAlgorithm = PgpSecurityConstants.OpenKeychainSymmetricKeyAlgorithmTags.USE_DEFAULT;
    protected long mSignatureMasterKeyId = Constants.key.none;
    protected Long mSignatureSubKeyId = null;
    protected int mSignatureHashAlgorithm = PgpSecurityConstants.OpenKeychainHashAlgorithmTags.USE_DEFAULT;
    protected long mAdditionalEncryptId = Constants.key.none;
    protected boolean mFailOnMissingEncryptionKeyIds = false;
    protected String mCharset;
    protected boolean mCleartextSignature;
    protected boolean mDetachedSignature = false;
    protected boolean mHiddenRecipients = false;
    protected boolean mIntegrityProtected = true;
    protected boolean mAddBackupHeader = false;

    public PgpSignEncryptInputParcel() {

    }

    PgpSignEncryptInputParcel(Parcel source) {

        ClassLoader loader = getClass().getClassLoader();

        // we do all of those here, so the PgpSignEncryptInput class doesn't have to be parcelable
        mVersionHeader = source.readString();
        mEnableAsciiArmorOutput  = source.readInt() == 1;
        mCompressionAlgorithm = source.readInt();
        mEncryptionMasterKeyIds = source.createLongArray();
        mSymmetricPassphrase = source.readParcelable(loader);
        mSymmetricEncryptionAlgorithm = source.readInt();
        mSignatureMasterKeyId = source.readLong();
        mSignatureSubKeyId = source.readInt() == 1 ? source.readLong() : null;
        mSignatureHashAlgorithm = source.readInt();
        mAdditionalEncryptId = source.readLong();
        mFailOnMissingEncryptionKeyIds = source.readInt() == 1;
        mCharset = source.readString();
        mCleartextSignature = source.readInt() == 1;
        mDetachedSignature = source.readInt() == 1;
        mHiddenRecipients = source.readInt() == 1;
        mIntegrityProtected = source.readInt() == 1;
        mAddBackupHeader = source.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mVersionHeader);
        dest.writeInt(mEnableAsciiArmorOutput ? 1 : 0);
        dest.writeInt(mCompressionAlgorithm);
        dest.writeLongArray(mEncryptionMasterKeyIds);
        dest.writeParcelable(mSymmetricPassphrase, 0);
        dest.writeInt(mSymmetricEncryptionAlgorithm);
        dest.writeLong(mSignatureMasterKeyId);
        if (mSignatureSubKeyId != null) {
            dest.writeInt(1);
            dest.writeLong(mSignatureSubKeyId);
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(mSignatureHashAlgorithm);
        dest.writeLong(mAdditionalEncryptId);
        dest.writeInt(mFailOnMissingEncryptionKeyIds ? 1 : 0);
        dest.writeString(mCharset);
        dest.writeInt(mCleartextSignature ? 1 : 0);
        dest.writeInt(mDetachedSignature ? 1 : 0);
        dest.writeInt(mHiddenRecipients ? 1 : 0);
        dest.writeInt(mIntegrityProtected ? 1 : 0);
        dest.writeInt(mAddBackupHeader ? 1 : 0);
    }

    public String getCharset() {
        return mCharset;
    }

    public void setCharset(String mCharset) {
        this.mCharset = mCharset;
    }

    public boolean isFailOnMissingEncryptionKeyIds() {
        return mFailOnMissingEncryptionKeyIds;
    }

    public long getAdditionalEncryptId() {
        return mAdditionalEncryptId;
    }

    public PgpSignEncryptInputParcel setAdditionalEncryptId(long additionalEncryptId) {
        mAdditionalEncryptId = additionalEncryptId;
        return this;
    }

    public int getSignatureHashAlgorithm() {
        return mSignatureHashAlgorithm;
    }

    public PgpSignEncryptInputParcel setSignatureHashAlgorithm(int signatureHashAlgorithm) {
        mSignatureHashAlgorithm = signatureHashAlgorithm;
        return this;
    }

    public Long getSignatureSubKeyId() {
        return mSignatureSubKeyId;
    }

    public PgpSignEncryptInputParcel setSignatureSubKeyId(long signatureSubKeyId) {
        mSignatureSubKeyId = signatureSubKeyId;
        return this;
    }

    public long getSignatureMasterKeyId() {
        return mSignatureMasterKeyId;
    }

    public PgpSignEncryptInputParcel setSignatureMasterKeyId(long signatureMasterKeyId) {
        mSignatureMasterKeyId = signatureMasterKeyId;
        return this;
    }

    public int getSymmetricEncryptionAlgorithm() {
        return mSymmetricEncryptionAlgorithm;
    }

    public PgpSignEncryptInputParcel setSymmetricEncryptionAlgorithm(int symmetricEncryptionAlgorithm) {
        mSymmetricEncryptionAlgorithm = symmetricEncryptionAlgorithm;
        return this;
    }

    public Passphrase getSymmetricPassphrase() {
        return mSymmetricPassphrase;
    }

    public PgpSignEncryptInputParcel setSymmetricPassphrase(Passphrase symmetricPassphrase) {
        mSymmetricPassphrase = symmetricPassphrase;
        return this;
    }

    public long[] getEncryptionMasterKeyIds() {
        return mEncryptionMasterKeyIds;
    }

    public PgpSignEncryptInputParcel setEncryptionMasterKeyIds(long[] encryptionMasterKeyIds) {
        mEncryptionMasterKeyIds = encryptionMasterKeyIds;
        return this;
    }

    public int getCompressionAlgorithm() {
        return mCompressionAlgorithm;
    }

    public PgpSignEncryptInputParcel setCompressionAlgorithm(int compressionAlgorithm) {
        mCompressionAlgorithm = compressionAlgorithm;
        return this;
    }

    public boolean isEnableAsciiArmorOutput() {
        return mEnableAsciiArmorOutput;
    }

    public String getVersionHeader() {
        return mVersionHeader;
    }

    public PgpSignEncryptInputParcel setVersionHeader(String versionHeader) {
        mVersionHeader = versionHeader;
        return this;
    }

    public PgpSignEncryptInputParcel setEnableAsciiArmorOutput(boolean enableAsciiArmorOutput) {
        mEnableAsciiArmorOutput = enableAsciiArmorOutput;
        return this;
    }

    public PgpSignEncryptInputParcel setFailOnMissingEncryptionKeyIds(boolean failOnMissingEncryptionKeyIds) {
        mFailOnMissingEncryptionKeyIds = failOnMissingEncryptionKeyIds;
        return this;
    }

    public PgpSignEncryptInputParcel setCleartextSignature(boolean cleartextSignature) {
        this.mCleartextSignature = cleartextSignature;
        return this;
    }

    public boolean isCleartextSignature() {
        return mCleartextSignature;
    }

    public PgpSignEncryptInputParcel setDetachedSignature(boolean detachedSignature) {
        this.mDetachedSignature = detachedSignature;
        return this;
    }

    public boolean isDetachedSignature() {
        return mDetachedSignature;
    }

    public PgpSignEncryptInputParcel setHiddenRecipients(boolean hiddenRecipients) {
        this.mHiddenRecipients = hiddenRecipients;
        return this;
    }

    public boolean isIntegrityProtected() {
        return mIntegrityProtected;
    }

    /**
     * Only use for testing! Never disable integrity protection!
     */
    public PgpSignEncryptInputParcel setIntegrityProtected(boolean integrityProtected) {
        this.mIntegrityProtected = integrityProtected;
        return this;
    }

    public PgpSignEncryptInputParcel setAddBackupHeader(boolean addBackupHeader) {
        this.mAddBackupHeader = addBackupHeader;
        return this;
    }

    public boolean isAddBackupHeader() {
        return mAddBackupHeader;
    }

    public boolean isHiddenRecipients() {
        return mHiddenRecipients;
    }

    public static final Creator<PgpSignEncryptInputParcel> CREATOR = new Creator<PgpSignEncryptInputParcel>() {
        public PgpSignEncryptInputParcel createFromParcel(final Parcel source) {
            return new PgpSignEncryptInputParcel(source);
        }

        public PgpSignEncryptInputParcel[] newArray(final int size) {
            return new PgpSignEncryptInputParcel[size];
        }
    };

}

